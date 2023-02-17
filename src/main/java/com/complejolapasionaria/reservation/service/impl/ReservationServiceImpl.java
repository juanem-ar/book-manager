package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.exceptions.ResourceNotFound;
import com.complejolapasionaria.reservation.mapper.IReservationMapper;
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
    public ReservationResponseDto adminReserve(ReservationRequestDto dto,Authentication authentication, Long userId, Long id ) throws Exception {
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));
        if (!iRentalUnitRepository.getReferenceById(id).getBuilding().getOwner().getEmail().equals(authentication.getName()))
            throw new ResourceNotFound("This resource doesn't belong you.");
        return reservationSave(dto,user,id);
    }

    @Override
    public ReservationResponseDto userReserve(ReservationRequestDto dto,Authentication authentication, Long id) throws Exception{
        User user = iUserRepository.findByEmail(authentication.getName());
        return reservationSave(dto,user,id);
    }

    @Transactional
    public ReservationResponseDto reservationSave(ReservationRequestDto dto, User user, Long id) throws Exception {

        validationToReserve(dto,id);

        Reservation reservation = iReservationMapper.toEntity(dto);
        reservationSettings(reservation, user, id);

        Reservation reservationReserved = iReservationRepository.save(reservation);

        ReservationResponseDto response =  iReservationMapper.toResponseDto(reservationReserved);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,reservation);
    }

    public void validationToReserve(ReservationRequestDto dto, Long id) throws Exception {

        if (!iRentalUnitRepository.existsById(id))
            throw new ResourceNotFound("Invalid rental unit id");

        if (dto.getCheckIn().equals(dto.getCheckOut()))
            throw new BadRequestException("check in and check out ares equals");

        if (dto.getCheckIn().isAfter(dto.getCheckOut()))
            throw new BadRequestException("Invalid date");

        if (iReservationRepository.existsByCheckInLessThanAndCheckOutGreaterThanAndDeletedAndUnitId(dto.getCheckOut(), dto.getCheckIn(),false, id))
            throw new ResourceNotFound("unit not available");
    }

    @Override
    public ReservationResponseDto getById(Long id, Authentication authentication) throws Exception {
        if (!iReservationRepository.getReferenceById(id).getUser().getEmail().equals(authentication.getName()))
            throw new ResourceNotFound("This resource doesn't belong you.");
        return getReservation(id);
    }

    @Override
    public ReservationResponseDto getByIdAndAdminRole(Long id, Authentication authentication) throws Exception {
        if (!iReservationRepository.getReferenceById(id).getUnit().getBuilding().getOwner().getEmail().equals(authentication.getName()))
            throw new ResourceNotFound("This resource doesn't belong you.");
        return getReservation(id);
    }

    @Override
    @Transactional
    public ReservationResponseDto update(ReservationRequestDto request, Long id, Long userId, Authentication authentication) throws Exception {
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));
        Reservation entity = iReservationRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid reservation id"));
        if (entity.getDeleted())
            throw new ResourceNotFound("This resource doesn't exists.");

        entity.setDeleted(true);
        iReservationRepository.save(entity);
        entity.setDeleted(false);

        validationToReserve(request,entity.getUnit().getId());

        entity.setAmountOfPeople(request.getAmountOfPeople());
        entity.setCheckIn(request.getCheckIn());
        entity.setCheckOut(request.getCheckOut());
        entity.setPercent(request.getPercent());
        entity.setCostPerNight(request.getCostPerNight());
        reservationSettings(entity, user, entity.getUnit().getId());
        Reservation entitySaved = iReservationRepository.save(entity);
        ReservationResponseDto response = iReservationMapper.toResponseDto(entitySaved);

        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,entitySaved);
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
