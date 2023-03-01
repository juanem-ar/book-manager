package com.reservation.manager.service.impl;

import com.reservation.manager.dto.CommerceBuildingRequestDto;
import com.reservation.manager.dto.CommerceBuildingResponseDto;
import com.reservation.manager.dto.page.CommerceBuildingPageDto;
import com.reservation.manager.exceptions.BadRequestException;
import com.reservation.manager.exceptions.ResourceNotFound;
import com.reservation.manager.mapper.ICommerceBuildingMapper;
import com.reservation.manager.model.CommerceBuilding;
import com.reservation.manager.model.User;
import com.reservation.manager.repository.ICommerceBuildingRepository;
import com.reservation.manager.repository.IUserRepository;
import com.reservation.manager.service.ICommerceBuildingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CommerceBuildingServiceImpl implements ICommerceBuildingService {
    public static final Integer COMMERCE_BUILDINGS_FOR_PAGE = 3;
    private final ICommerceBuildingRepository iCommerceBuildingRepository;
    private final ICommerceBuildingMapper iCommerceBuildingMapper;
    private final IUserRepository iUserRepository;

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
            throw new ResourceNotFound("Invalid commerce id");
        CommerceBuilding entity = iCommerceBuildingRepository.getReferenceById(id);
        if(entity.getDeleted())
            throw new ResourceNotFound("Invalid commerce id");
        CommerceBuildingResponseDto response = iCommerceBuildingMapper.toCommerceBuildingResponseDto(entity);
        setOwnerDetails(entity,response);
        return response;
    }

    @Override
    public CommerceBuildingPageDto getAllCommerceBuildings(int page , HttpServletRequest httpServletRequest) throws Exception {
        if (page <= 0)
            throw new ResourceNotFound("You request page not found, try page 1");

        Pageable pageWithThreeElementsAndSortedByIdAscAndNameDesc = PageRequest.of(page-1, COMMERCE_BUILDINGS_FOR_PAGE,
                Sort.by("id")
                        .ascending()
                        .and(Sort.by("name")
                                .descending()));

        Page<CommerceBuilding> list = iCommerceBuildingRepository.findAllByDeleted(false, pageWithThreeElementsAndSortedByIdAscAndNameDesc);

        //Pagination DTO
        CommerceBuildingPageDto pagination = new CommerceBuildingPageDto();
        int totalPages = list.getTotalPages();
        pagination.setTotalPages(totalPages);

        if (page > totalPages)
            throw new ResourceNotFound("The page your request not found, try page 1 or go to previous page");

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
        pagination.setCommerceBuildingDtoList(responseList);
        return pagination;
    }

    @Override
    public CommerceBuildingResponseDto updateCommerceBuilding(Long id, Authentication authentication, CommerceBuildingRequestDto dto) throws Exception {
        if(!iCommerceBuildingRepository.existsById(id))
            throw new InvalidParameterException("Invalid commerce id");

        User user = iUserRepository.findByEmail(authentication.getName());
        CommerceBuilding entity = iCommerceBuildingRepository.getReferenceByIdAndOwner(id,user);
        if(entity.getDeleted())
            throw new ResourceNotFound("Resource removed.");
        CommerceBuilding entityUpdated = iCommerceBuildingMapper.updateEntity(dto, entity);
        iCommerceBuildingRepository.save(entityUpdated);
        CommerceBuildingResponseDto response = iCommerceBuildingMapper.toCommerceBuildingResponseDto(entityUpdated);
        setOwnerDetails(entityUpdated,response);
        return response;
    }

    @Override
    public String removeCommerceBuilding(Long id, Authentication authentication) throws Exception {
        CommerceBuilding entitySelected = iCommerceBuildingRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Not exists commerce building with id number: "+ id));
        if(!entitySelected.getOwner().getEmail().equals(authentication.getName()))
            throw new ResourceNotFound("You don't have permission to delete this commerce building");
        entitySelected.setDeleted(true);
        iCommerceBuildingRepository.save(entitySelected);
        return "Commerce Building removed.";
    }

    public void setOwnerDetails(CommerceBuilding cm, CommerceBuildingResponseDto response){
        Map<String,String> ownerDetails = new HashMap<>();
        ownerDetails.put("email",cm.getOwner().getEmail());
        ownerDetails.put("address",cm.getOwner().getAddress());
        ownerDetails.put("phone number",cm.getOwner().getPhoneNumber());
        response.setOwnerDetails(ownerDetails);
    }
}
