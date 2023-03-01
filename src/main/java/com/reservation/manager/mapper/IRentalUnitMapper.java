package com.reservation.manager.mapper;

import com.reservation.manager.Enum.EPool;
import com.reservation.manager.dto.RentalUnitAdminResponseDto;
import com.reservation.manager.dto.RentalUnitPatchRequestDto;
import com.reservation.manager.dto.RentalUnitRequestDto;
import com.reservation.manager.dto.RentalUnitResponseDto;
import com.reservation.manager.exceptions.BadRequestException;
import com.reservation.manager.model.RentalUnit;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IRentalUnitMapper {

    @Mapping( source = "buildingId",target = "building.id")
    RentalUnit toEntity(RentalUnitRequestDto dto);
    @Mapping(target = "buildingId",source = "building.id")
    RentalUnitResponseDto toRentalUnitResponseDto(RentalUnit entity);
    List<RentalUnitResponseDto> toRentalUnitResponseDtoList(List<RentalUnit> list);
    RentalUnit updateEntity(RentalUnitPatchRequestDto dto, @MappingTarget RentalUnit entity);

    RentalUnitAdminResponseDto toRentalUnitAdminResponseDto(RentalUnit entity);

    default EPool stringToEPool(String dto) throws BadRequestException {
        if (dto.equalsIgnoreCase("PRIVATE") || dto.equalsIgnoreCase("privada"))
            return EPool.POOL_PRIVATE;
        else if(dto.equalsIgnoreCase("PUBLIC") || dto.equalsIgnoreCase("SHARED"))
            return EPool.POOL_SHARED;
        else
            throw new BadRequestException("Invalid pool type. Please insert 'private' or 'shared'");
    }
}
