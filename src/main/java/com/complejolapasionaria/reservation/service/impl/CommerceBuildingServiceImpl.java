package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.dto.TransactionPageDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.mapper.ICommerceBuildingMapper;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.ICommerceBuildingRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.ICommerceBuildingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommerceBuildingServiceImpl implements ICommerceBuildingService {
    public static final Integer TRANSACTIONS_FOR_PAGE = 3;
    private final ICommerceBuildingRepository iCommerceBuildingRepository;
    private final ICommerceBuildingMapper iCommerceBuildingMapper;
    private final IUserRepository iUserRepository;

    public CommerceBuildingServiceImpl(ICommerceBuildingRepository iCommerceBuildingRepository, ICommerceBuildingMapper iCommerceBuildingMapper, IUserRepository iUserRepository) {
        this.iCommerceBuildingRepository = iCommerceBuildingRepository;
        this.iCommerceBuildingMapper = iCommerceBuildingMapper;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public CommerceBuildingResponseDto save(CommerceBuildingRequestDto dto, Authentication authentication) throws Exception {
        if(iCommerceBuildingRepository.existsByName(dto.getName()))
            throw new BadRequestException("There is an commerce building with that name.");
        CommerceBuilding entity = iCommerceBuildingMapper.toEntity(dto);
        User owner = iUserRepository.findByEmail(authentication.getName());
        entity.setOwner(owner);
        entity.setDeleted(false);
        CommerceBuilding entitySaved = iCommerceBuildingRepository.save(entity);
        CommerceBuildingResponseDto response = iCommerceBuildingMapper.toCommerceBuildingResponseDto(entitySaved);
        setOwnerDetails(entitySaved,response);
        return response;
    }

    @Override
    public CommerceBuildingResponseDto getCommerceBuildingById(Long id) throws Exception {
        if (!iCommerceBuildingRepository.existsById(id))
            throw new InvalidParameterException("Invalid commerce id");
        CommerceBuilding entity = iCommerceBuildingRepository.getReferenceById(id);
        CommerceBuildingResponseDto response = iCommerceBuildingMapper.toCommerceBuildingResponseDto(entity);
        setOwnerDetails(entity,response);
        return response;
    }

    @Override
    public TransactionPageDto getAllCommerceBuildingsByUserLogged(int page, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (page <= 0)
            throw new InvalidParameterException("You request page not found, try page 1");

        Pageable pageWithTenElementsAndSortedByIdAscAndNameDesc = PageRequest.of(page-1,TRANSACTIONS_FOR_PAGE,
                Sort.by("id")
                        .ascending()
                        .and(Sort.by("name")
                                .descending()));

        User user = iUserRepository.findByEmail(authentication.getName());
        Page<CommerceBuilding> list = iCommerceBuildingRepository.findAllByOwner(user, pageWithTenElementsAndSortedByIdAscAndNameDesc);


        //Pagination DTO
        TransactionPageDto pagination = new TransactionPageDto();
        int totalPages = list.getTotalPages();
        pagination.setTotalPages(totalPages);

        if (page > totalPages)
            throw new InvalidParameterException("The page your request not found, try page 1 or go to previous page");

        String url = httpServletRequest
                .getRequestURL().toString() + "?" + "page=";

        pagination.setNextPage(totalPages == page ? null : url + String.valueOf(page + 1));
        pagination.setPreviousPage(page == 1 ? null : url + String.valueOf(page - 1));

        List<CommerceBuildingResponseDto> responseList = iCommerceBuildingMapper.toCommerceBuildingResponseDtoList(list.getContent());
        for(CommerceBuildingResponseDto dto : responseList){
            for(CommerceBuilding entity : list) {
                setOwnerDetails(entity,dto);
            }
        }
        pagination.setTransactionDtoList(responseList);
        return pagination;
    }

    public void setOwnerDetails(CommerceBuilding cm, CommerceBuildingResponseDto response){
        Map<String,String> ownerDetails = new HashMap<>();
        ownerDetails.put("email",cm.getOwner().getEmail());
        ownerDetails.put("address",cm.getOwner().getAddress());
        ownerDetails.put("phone number",cm.getOwner().getPhoneNumber());
        response.setOwnerDetails(ownerDetails);
    }
}
