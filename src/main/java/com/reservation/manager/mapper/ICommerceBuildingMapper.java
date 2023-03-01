package com.reservation.manager.mapper;

import com.reservation.manager.dto.CommerceBuildingRequestDto;
import com.reservation.manager.dto.CommerceBuildingResponseDto;
import com.reservation.manager.model.CommerceBuilding;
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
