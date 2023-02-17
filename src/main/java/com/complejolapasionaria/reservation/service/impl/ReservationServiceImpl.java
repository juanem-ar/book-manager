package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.exceptions.ResourceNotFound;
import com.complejolapasionaria.reservation.mapper.IReservationMapper;
import com.complejolapasionaria.reservation.model.RentalUnit;
import com.complejolapasionaria.reservation.model.Reservation;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IRentalUnitRepository;
import com.complejolapasionaria.reservation.repository.IReservationRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.IReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IUserRepository iUserRepository;
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IReservationRepository iReservationRepository;
    private final IReservationMapper iReservationMapper;

    public ReservationServiceImpl(IUserRepository iUserRepository, IRentalUnitRepository iRentalUnitRepository, IReservationRepository iReservationRepository, IReservationMapper iReservationMapper) {
        this.iUserRepository = iUserRepository;
        this.iRentalUnitRepository = iRentalUnitRepository;
        this.iReservationRepository = iReservationRepository;
        this.iReservationMapper = iReservationMapper;
    }

    @Override
    public ReservationResponseDto adminReserve(ReservationRequestDto dto,Authentication authentication, Long userId, Long rentalUnitId ) throws Exception {
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));
        rolesValidations(rentalUnitId,authentication,true, true);
        return reservationSave(dto,user,rentalUnitId);
    }

    @Override
    public ReservationResponseDto userReserve(ReservationRequestDto dto,Authentication authentication, Long id) throws Exception{
        User user = iUserRepository.findByEmail(authentication.getName());
        return reservationSave(dto,user,id);
    }

    @Transactional
    public ReservationResponseDto reservationSave(ReservationRequestDto dto, User user, Long id) throws Exception {
        validationToReserve(dto,id,false);
        Reservation reservation = iReservationMapper.toEntity(dto);
        reservationSettings(reservation, user, id);
        Reservation reservationReserved = iReservationRepository.save(reservation);
        ReservationResponseDto response =  iReservationMapper.toResponseDto(reservationReserved);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,reservation);
    }

    public void rolesValidations(Long id, Authentication authentication,boolean isCreate, boolean isAdmin) throws ResourceNotFound {
        if(isCreate){
            if (!iRentalUnitRepository.getReferenceById(id).getBuilding().getOwner().getEmail().equals(authentication.getName()))
                throw new ResourceNotFound("This resource doesn't belong you.");
        }else{
            if (isAdmin){
                if (!iReservationRepository.getReferenceById(id).getUnit().getBuilding().getOwner().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This resource doesn't belong you.");
            }else{
                if (!iReservationRepository.getReferenceById(id).getUser().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This resource doesn't belong you.");
            }
        }
    }

    public void validationToReserve(ReservationRequestDto dto, Long id, boolean isCreated) throws Exception {
        RentalUnit rentalUnit = iRentalUnitRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid rental unit id"));
        if (!rentalUnit.getStatus().equals(EStatus.STATUS_ENABLE))
            throw new ResourceNotFound("Rental unit is locked");

        if (dto.getCheckIn().equals(dto.getCheckOut()))
            throw new BadRequestException("check in and check out ares equals");

        if (dto.getCheckIn().isAfter(dto.getCheckOut()))
            throw new BadRequestException("Invalid date");
        if(!isCreated){
            if (iReservationRepository.existsByCheckInLessThanAndCheckOutGreaterThanAndDeletedAndUnitId(dto.getCheckOut(), dto.getCheckIn(),false, id))
                throw new ResourceNotFound("unit not available");
        }
    }

    @Override
    public ReservationResponseDto getById(Long id, Authentication authentication) throws Exception {
        rolesValidations(id,authentication,false,false);
        return getReservation(id);
    }

    @Override
    public ReservationResponseDto getByIdAndAdminRole(Long id, Authentication authentication) throws Exception {
        rolesValidations(id,authentication,true,false);
        return getReservation(id);
    }

    @Override
    @Transactional
    public ReservationResponseDto update(ReservationRequestDto request, Long id, Long userId, Authentication authentication) throws Exception {
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));

        Reservation entity = remove(id,authentication);
        entity.setDeleted(false);
        validationToReserve(request,entity.getUnit().getId(),true);

        if (iReservationRepository.existsByIdNotAndCheckInLessThanAndCheckOutGreaterThanAndDeletedAndUnitId(id, request.getCheckOut(), request.getCheckIn(),false, entity.getUnit().getId()))
            throw new ResourceNotFound("unit not available");

        entity.setAmountOfPeople(request.getAmountOfPeople());
        entity.setCheckIn(request.getCheckIn());
        entity.setCheckOut(request.getCheckOut());
        entity.setPercent(request.getPercent());
        entity.setCostPerNight(request.getCostPerNight());
        entity.setStatus(EStatus.STATUS_ACCEPTED);
        reservationSettings(entity, user, entity.getUnit().getId());
        Reservation entitySaved = iReservationRepository.save(entity);
        ReservationResponseDto response = iReservationMapper.toResponseDto(entitySaved);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,entitySaved);
    }
    @Override
    public String removeReservation(Long id, Authentication authentication)throws Exception {
        Reservation entity = remove(id, authentication);
        return "Reservation id: " + id + " removed by admin: " + entity.getUnit().getBuilding().getOwner().getFirstName() + " " + entity.getUnit().getBuilding().getOwner().getLastName();
    }

    @Override
    public String confirmReservation(Long id, Authentication authentication) throws Exception {
        Reservation entity = ownerValidations(id,authentication);
        entity.setStatus(EStatus.STATUS_ACCEPTED);
        iReservationRepository.save(entity);
        return "Reservation id: " + id + " accepted by admin: " + entity.getUnit().getBuilding().getOwner().getFirstName() + " " + entity.getUnit().getBuilding().getOwner().getLastName();
    }

    public Reservation remove(Long id, Authentication authentication) throws Exception {
        Reservation entity = ownerValidations(id,authentication);
        entity.setDeleted(true);
        entity.setStatus(EStatus.STATUS_DISABLE);
        return iReservationRepository.save(entity);
    }
    public Reservation ownerValidations(Long id, Authentication authentication) throws Exception{
        Reservation entity = iReservationRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid reservation id"));
        rolesValidations(id,authentication,false,true);
        if (entity.getDeleted())
            throw new ResourceNotFound("This resource doesn't exists.");
        return entity;
}
    public ReservationResponseDto getReservation(Long id)throws Exception{
        Reservation entity = iReservationRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid reservation id"));
        ReservationResponseDto response = iReservationMapper.toResponseDto(entity);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,entity);
    }

    public ReservationResponseDto setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(ReservationResponseDto dto, Reservation entity){
        dto.setFullName(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        dto.setUnitName(entity.getUnit().getName());
        dto.setPhone(entity.getUser().getPhoneNumber());
        return dto;
    }

    public Reservation reservationSettings(Reservation entity,User user, Long id){
        entity.setUnit(iRentalUnitRepository.getReferenceById(id));
        entity.setDeleted(Boolean.FALSE);
        entity.setStatus(EStatus.STATUS_IN_PROCESS);
        entity.setUser(user);
        long daysReserved = DAYS.between(entity.getCheckIn(), entity.getCheckOut());
        entity.setTotalAmount(entity.getCostPerNight() * daysReserved);
        entity.setPartialPayment(entity.getTotalAmount()*entity.getPercent()/100);
        entity.setDebit(entity.getTotalAmount()-entity.getPartialPayment());
        return entity;
    }
}
