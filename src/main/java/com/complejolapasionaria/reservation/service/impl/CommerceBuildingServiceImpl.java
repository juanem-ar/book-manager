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
import org.springframework.stereotype.Service;

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
}
