package com.complejolapasionaria.reservation.mapper;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ICommerceBuildingMapper {
    CommerceBuilding toEntity(CommerceBuildingRequestDto dto);
    CommerceBuildingResponseDto toCommerceBuildingResponseDto(CommerceBuilding entity);
    List<CommerceBuildingResponseDto> toCommerceBuildingResponseDtoList(List<CommerceBuilding> entityList);
    CommerceBuilding updateEntity(CommerceBuildingRequestDto dto, @MappingTarget CommerceBuilding target);
}
