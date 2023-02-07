package com.complejolapasionaria.reservation.mapper;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICommerceBuildingMapper {
    CommerceBuilding toEntity(CommerceBuildingRequestDto dto);

    CommerceBuildingResponseDto toCommerceBuildingResponseDto(CommerceBuilding entity);
}
