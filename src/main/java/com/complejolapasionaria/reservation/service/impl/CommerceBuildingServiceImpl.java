package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.mapper.ICommerceBuildingMapper;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.ICommerceBuildingRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.ICommerceBuildingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.directory.InvalidAttributesException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommerceBuildingServiceImpl implements ICommerceBuildingService {
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
        return iCommerceBuildingMapper.toCommerceBuildingResponseDto(entitySaved);
    }

    @Override
    public CommerceBuildingResponseDto getCommerceBuildingById(Long id) throws Exception {
        if (!iCommerceBuildingRepository.existsById(id))
            throw new InvalidParameterException("Invalid commerce id");
        CommerceBuilding entity = iCommerceBuildingRepository.getReferenceById(id);
        CommerceBuildingResponseDto response = iCommerceBuildingMapper.toCommerceBuildingResponseDto(entity);
        Map<String,String> ownerDetails = new HashMap<>();
        ownerDetails.put("email",entity.getOwner().getEmail());
        ownerDetails.put("address",entity.getOwner().getAddress());
        ownerDetails.put("phone number",entity.getOwner().getPhoneNumber());
        response.setOwnerDetails(ownerDetails);
        return response;
    }
}
