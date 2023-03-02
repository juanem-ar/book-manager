package com.reservation.manager.service.impl;

import com.reservation.manager.Enum.ERoles;
import com.reservation.manager.Enum.EStatus;
import com.reservation.manager.dto.ReservationRequestDto;
import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.dto.page.ReservationPageDto;
import com.reservation.manager.exceptions.BadRequestException;
import com.reservation.manager.exceptions.ResourceNotFound;
import com.reservation.manager.mapper.IReservationMapper;
import com.reservation.manager.model.RentalUnit;
import com.reservation.manager.model.Reservation;
import com.reservation.manager.model.User;
import com.reservation.manager.repository.IRentalUnitRepository;
import com.reservation.manager.repository.IReservationRepository;
import com.reservation.manager.repository.IUserRepository;
import com.reservation.manager.service.IEmailService;
import com.reservation.manager.service.IReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IUserRepository iUserRepository;
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IReservationRepository iReservationRepository;
    private final IReservationMapper iReservationMapper;

    private final IEmailService iEmailService;

    @Override
    public ReservationResponseDto adminReserve(ReservationRequestDto dto, Authentication authentication, Long userId, Long rentalUnitId ) throws Exception {
        RentalUnit rentalUnit = iRentalUnitRepository.findById(rentalUnitId).orElseThrow(()->new ResourceNotFound("Invalid rental unit id"));
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));
        rolesValidations(rentalUnitId,authentication,true, true);
        return reservationSave(dto,user,rentalUnit);
    }

    @Override
    public ReservationResponseDto userReserve(ReservationRequestDto dto,Authentication authentication, Long id) throws Exception{
        User user = iUserRepository.findByEmail(authentication.getName());
        RentalUnit rentalUnit = iRentalUnitRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid rental unit id"));
        ReservationResponseDto response = reservationSave(dto,user,rentalUnit);
        iEmailService.sendReservationCreatedEmailTo(user.getEmail(), response);
        return response;
    }

    @Transactional
    public ReservationResponseDto reservationSave(ReservationRequestDto dto, User user, RentalUnit rentalUnit) throws Exception {
        validationToReserve(dto,rentalUnit,false);
        Reservation reservation = iReservationMapper.toEntity(dto);
        reservationSettings(reservation, user, rentalUnit.getId());
        Reservation reservationReserved = iReservationRepository.save(reservation);
        ReservationResponseDto response =  iReservationMapper.toResponseDto(reservationReserved);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,reservation);
    }

    public void rolesValidations(Long id, Authentication authentication,boolean isCreate, boolean isAdmin) throws ResourceNotFound {
        if(isCreate){
            if (isAdmin) {
                if (!iRentalUnitRepository.getReferenceById(id).getBuilding().getOwner().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This rental unit doesn't belong you.");
            }else{
                if (!iReservationRepository.getReferenceById(id).getUser().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This reservation doesn't belong you.");
            }
        }else{
            if (isAdmin){
                if (!iReservationRepository.getReferenceById(id).getUnit().getBuilding().getOwner().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This reservation doesn't belong you.");
            }else{
                if (!iReservationRepository.getReferenceById(id).getUser().getEmail().equals(authentication.getName()))
                    throw new ResourceNotFound("This reservation doesn't belong you.");
            }
        }
    }

    public void validationToReserve(ReservationRequestDto dto, RentalUnit rentalUnit, boolean isCreated) throws Exception {

        if (!rentalUnit.getStatus().equals(EStatus.STATUS_ENABLE))
            throw new ResourceNotFound("Rental unit is locked");

        if (dto.getCheckIn().equals(dto.getCheckOut()))
            throw new BadRequestException("check in and check out ares equals");

        if (dto.getCheckIn().isAfter(dto.getCheckOut()))
            throw new BadRequestException("Invalid date");
        if(!isCreated){
            if (iReservationRepository.existsByCheckInLessThanAndCheckOutGreaterThanAndDeletedAndUnitId(dto.getCheckOut(), dto.getCheckIn(),false, rentalUnit.getId()))
                throw new ResourceNotFound("unit not available");
        }
    }

    @Override
    public ReservationResponseDto getById(Long id, Authentication authentication) throws Exception {
        User user = iUserRepository.findByEmail(authentication.getName());
        Reservation entity = iReservationRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid reservation id"));
        if (user.getRole().getName().equals(ERoles.ROLE_USER))
            rolesValidations(id,authentication,true,false);
        else
            rolesValidations(entity.getUnit().getId(),authentication,true,true);
        return getReservation(entity);
    }

    @Override
    @Transactional
    public ReservationResponseDto update(ReservationRequestDto request, Long id, Long userId, Authentication authentication) throws Exception {
        User user = iUserRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("Invalid id"));

        Reservation entity = remove(id,authentication);
        entity.setDeleted(false);
        validationToReserve(request,entity.getUnit(),true);

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
        iEmailService.sendReservationConfirmEmailTo(entity.getUser().getEmail(),entity);
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
    public ReservationResponseDto getReservation(Reservation entity)throws Exception{
        ReservationResponseDto response = iReservationMapper.toResponseDto(entity);
        return setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(response,entity);
    }

    public ReservationResponseDto setFullNameAndUnitNameAndPhoneOfReservationResponseFromReservation(ReservationResponseDto dto, Reservation entity){
        dto.setFullName(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        dto.setUnitName(entity.getUnit().getName());
        dto.setPhone(entity.getUser().getPhoneNumber());
        return dto;
    }

    public ReservationPageDto getAllReservationsByRentalUnitId(int page, HttpServletRequest httpServletRequest, Long rentalUnitId, int reservationsUnits) throws Exception {
        if (page <= 0)
            throw new ResourceNotFound("You request page not found, try page 1");

        Pageable pageWithUndefinedElementsAndSortedByCheckInAsc = PageRequest.of(page-1,reservationsUnits,
                Sort.by("checkIn")
                        .ascending());

        Page<Reservation> list = iReservationRepository.findAllByDeletedAndUnitId(false,rentalUnitId, pageWithUndefinedElementsAndSortedByCheckInAsc);

        //Pagination DTO
        ReservationPageDto pagination = new ReservationPageDto();
        int totalPages = list.getTotalPages();
        pagination.setTotalPages(totalPages);

        if (page > totalPages)
            throw new ResourceNotFound("The page your request not found, try page 1 or go to previous page");

        String url = httpServletRequest
                .getRequestURL().toString() + "?" + "page=";

        pagination.setNextPage(totalPages == page ? null : url + String.valueOf(page + 1));
        pagination.setPreviousPage(page == 1 ? null : url + String.valueOf(page - 1));

        List<ReservationResponseDto> responseList = new ArrayList<>();
        for (Reservation re : list.getContent()){
            responseList.add(getReservation(re));
        }
        pagination.setReservationDtoList(responseList);
        return pagination;
    }

    public void reservationSettings(Reservation entity, User user, Long id){
        entity.setUnit(iRentalUnitRepository.getReferenceById(id));
        entity.setDeleted(Boolean.FALSE);
        entity.setStatus(EStatus.STATUS_IN_PROCESS);
        entity.setUser(user);
        long daysReserved = DAYS.between(entity.getCheckIn(), entity.getCheckOut());
        entity.setTotalAmount(entity.getCostPerNight() * daysReserved);
        entity.setPartialPayment(entity.getTotalAmount()*entity.getPercent()/100);
        entity.setDebit(entity.getTotalAmount()-entity.getPartialPayment());
    }
}
