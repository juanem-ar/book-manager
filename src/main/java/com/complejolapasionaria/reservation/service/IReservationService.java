package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import org.springframework.security.core.Authentication;

public interface IReservationService {
    ReservationResponseDto adminReserve(ReservationRequestDto dto,Authentication authentication, Long userId, Long id ) throws Exception;
    ReservationResponseDto userReserve(ReservationRequestDto dto, Authentication authentication, Long id) throws Exception;
    ReservationResponseDto getById(Long id, Authentication authentication) throws Exception;
    ReservationResponseDto getByIdAndAdminRole(Long id, Authentication authentication) throws Exception;
    ReservationResponseDto update(ReservationRequestDto dto, Long id, Long userId, Authentication authentication) throws Exception;
}
