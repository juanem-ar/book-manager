package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.RentalUnitRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitResponseDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.mapper.IRentalUnitMapper;
import com.complejolapasionaria.reservation.model.RentalUnit;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.ICommerceBuildingRepository;
import com.complejolapasionaria.reservation.repository.IRentalUnitRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.IRentalUnitService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RentalUnitServiceImpl implements IRentalUnitService {
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IRentalUnitMapper iRentalUnitMapper;
    private final ICommerceBuildingRepository iCommerceBuildingRepository;
    private final IUserRepository iUserRepository;

    public RentalUnitServiceImpl(IRentalUnitRepository iRentalUnitRepository, IRentalUnitMapper iRentalUnitMapper, ICommerceBuildingRepository iCommerceBuildingRepository, IUserRepository iUserRepository) {
        this.iRentalUnitRepository = iRentalUnitRepository;
        this.iRentalUnitMapper = iRentalUnitMapper;
        this.iCommerceBuildingRepository = iCommerceBuildingRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public RentalUnitResponseDto save(RentalUnitRequestDto dto, Authentication authentication) throws Exception {

        User user = iUserRepository.findByEmail(authentication.getName());

        if(iRentalUnitRepository.existsByName(dto.getName()))
            throw new BadRequestException("There is a rental unit with that name.");

        if (!iCommerceBuildingRepository.existsByIdAndOwner(dto.getBuildingId(),user))
            throw new BadRequestException("Invalid commerce building id");

        RentalUnit entity = iRentalUnitMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        entity.setStatus(EStatus.STATUS_ENABLE);

        RentalUnit entitySaved = iRentalUnitRepository.save(entity);
        return iRentalUnitMapper.toRentalUnitResponseDto(entitySaved);
    }
}
