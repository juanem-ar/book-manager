package com.reservation.manager.mapper;

import com.reservation.manager.dto.ReservationRequestDto;
import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IReservationMapper {
    Reservation toEntity(ReservationRequestDto dto);
    ReservationResponseDto toResponseDto(Reservation entity);

}
