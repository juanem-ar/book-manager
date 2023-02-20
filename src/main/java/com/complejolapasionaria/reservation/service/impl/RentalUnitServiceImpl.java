package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.*;
import com.complejolapasionaria.reservation.dto.page.RentalUnitPageDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.exceptions.ResourceNotFound;
import com.complejolapasionaria.reservation.mapper.IRentalUnitMapper;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
import com.complejolapasionaria.reservation.model.RentalUnit;
import com.complejolapasionaria.reservation.model.Reservation;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.ICommerceBuildingRepository;
import com.complejolapasionaria.reservation.repository.IRentalUnitRepository;
import com.complejolapasionaria.reservation.repository.IReservationRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.IRentalUnitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalUnitServiceImpl implements IRentalUnitService {

    public static final Integer RENTAL_UNITS_FOR_PAGE = 3;
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IRentalUnitMapper iRentalUnitMapper;
    private final ICommerceBuildingRepository iCommerceBuildingRepository;
    private final IUserRepository iUserRepository;
    private final IReservationRepository iReservationRepository;
    private final ReservationServiceImpl iReservationService;

    public RentalUnitServiceImpl(IRentalUnitRepository iRentalUnitRepository, IRentalUnitMapper iRentalUnitMapper, ICommerceBuildingRepository iCommerceBuildingRepository, IUserRepository iUserRepository, IReservationRepository iReservationRepository, ReservationServiceImpl iReservationService) {
        this.iRentalUnitRepository = iRentalUnitRepository;
        this.iRentalUnitMapper = iRentalUnitMapper;
        this.iCommerceBuildingRepository = iCommerceBuildingRepository;
        this.iUserRepository = iUserRepository;
        this.iReservationRepository = iReservationRepository;
        this.iReservationService = iReservationService;
    }

    @Override
    public RentalUnitResponseDto save(RentalUnitRequestDto dto, Authentication authentication) throws Exception {

        User user = iUserRepository.findByEmail(authentication.getName());

        CommerceBuilding commerce = iCommerceBuildingRepository.getReferenceById(dto.getBuildingId());

        if (!iCommerceBuildingRepository.existsByIdAndOwner(dto.getBuildingId(),user))
            throw new BadRequestException("Invalid commerce building id");
        if(iRentalUnitRepository.existsByNameAndBuilding(dto.getName(),commerce))
            throw new BadRequestException("There is a rental unit with that name.");

        RentalUnit entity = iRentalUnitMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        entity.setStatus(EStatus.STATUS_ENABLE);

        RentalUnit entitySaved = iRentalUnitRepository.save(entity);
        return iRentalUnitMapper.toRentalUnitResponseDto(entitySaved);
    }

    @Override
    public RentalUnitResponseDto getRentalUnitById(Long id) throws Exception {
        RentalUnit entity = iRentalUnitRepository.findById(id).orElseThrow(()->new ResourceNotFound("Not exists rental unit with id number: "+ id));
        if(entity.getDeleted())
            throw new ResourceNotFound("Resource removed.");
        return iRentalUnitMapper.toRentalUnitResponseDto(entity);
    }

    @Override
    public RentalUnitPageDto getAllRentalUnit(int page, HttpServletRequest httpServletRequest) throws Exception {
        if (page <= 0)
            throw new ResourceNotFound("You request page not found, try page 1");

        Pageable pageWithThreeElementsAndSortedByOwnerIdAscAndIdDesc = PageRequest.of(page-1,RENTAL_UNITS_FOR_PAGE,
                Sort.by("building")
                        .ascending()
                        .and(Sort.by("id")
                                .descending()));

        Page<RentalUnit> list = iRentalUnitRepository.findAllByDeleted(false, pageWithThreeElementsAndSortedByOwnerIdAscAndIdDesc);

        //Pagination DTO
        RentalUnitPageDto pagination = new RentalUnitPageDto();
        int totalPages = list.getTotalPages();
        pagination.setTotalPages(totalPages);

        if (page > totalPages)
            throw new ResourceNotFound("The page your request not found, try page 1 or go to previous page");

        String url = httpServletRequest
                .getRequestURL().toString() + "?" + "page=";

        pagination.setNextPage(totalPages == page ? null : url + String.valueOf(page + 1));
        pagination.setPreviousPage(page == 1 ? null : url + String.valueOf(page - 1));

        List<RentalUnitResponseDto> responseList = iRentalUnitMapper.toRentalUnitResponseDtoList(list.getContent());
        pagination.setRentalUnitDtoList(responseList);
        return pagination;
    }

    @Override
    public RentalUnitResponseDto updateRentalUnit(Long id, RentalUnitPatchRequestDto dto, Authentication authentication) throws Exception {
        RentalUnit entity = ownerValidations(id,authentication);
        RentalUnit entityUpdated = iRentalUnitMapper.updateEntity(dto, entity);
        iRentalUnitRepository.save(entityUpdated);
        return iRentalUnitMapper.toRentalUnitResponseDto(entityUpdated);
    }

    @Override
    public String removeRentalUnit(Long id, Authentication authentication) throws Exception {
        RentalUnit entity = ownerValidations(id,authentication);
        entity.setDeleted(true);
        entity.setStatus(EStatus.STATUS_LOCKED);
        iRentalUnitRepository.save(entity);
        return "Rental unit id: " + id + " removed by admin: " + entity.getBuilding().getOwner().getFirstName() + " " + entity.getBuilding().getOwner().getLastName();
    }

    @Override
    public RentalUnitAdminResponseDto getRentalUnitByAdmin(Long id, Authentication authentication) throws Exception {
        RentalUnit entity = ownerValidations(id,authentication);
        RentalUnitAdminResponseDto adminResponse = iRentalUnitMapper.toRentalUnitAdminResponseDto(entity);

        List<Reservation> reservationList = iReservationRepository.findAllByDeletedAndUnitId(false,id);
        List<ReservationResponseDto> reservationResponseList = new ArrayList<>();

        for (Reservation re : reservationList){
            reservationResponseList.add(iReservationService.getReservation(re));
        }
        adminResponse.setReservationList(reservationResponseList);
        
        adminResponse.setBuildingName(entity.getBuilding().getName());
        return adminResponse;
    }

    @Override
    public String lockRentalUnit(Long id, Authentication authentication) throws Exception {
        RentalUnit entity = ownerValidations(id,authentication);
        entity.setStatus(EStatus.STATUS_LOCKED);
        iRentalUnitRepository.save(entity);
        return "Rental unit id: " + id + " locked by admin: " + entity.getBuilding().getOwner().getFirstName() + " " + entity.getBuilding().getOwner().getLastName();
    }
    @Override
    public String enableRentalUnit(Long id, Authentication authentication) throws Exception {
        RentalUnit entity = ownerValidations(id,authentication);
        entity.setStatus(EStatus.STATUS_ENABLE);
        iRentalUnitRepository.save(entity);
        return "Rental unit id: " + id + " enabled by admin: " + entity.getBuilding().getOwner().getFirstName() + " " + entity.getBuilding().getOwner().getLastName();
    }
    public RentalUnit ownerValidations(Long id, Authentication authentication) throws Exception{
        RentalUnit entity = iRentalUnitRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid rental unit id"));
        if(!entity.getBuilding().getOwner().getEmail().equals(authentication.getName()))
            throw new ResourceNotFound("You don't have permission to lock this rental unit");
        if(entity.getDeleted())
            throw new ResourceNotFound("It's resource doesn't exists.");
        return entity;
    }
}
