package com.complejolapasionaria.reservation.mapper;

import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IReservationMapper {
    Reservation toEntity(ReservationRequestDto dto);
    ReservationResponseDto toResponseDto(Reservation entity);
    List<ReservationResponseDto> toReservationResponseDtoList(List<Reservation> list);
    ReservationRequestDto toReservationRequestDto(Reservation entity);
}
