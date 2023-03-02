package com.reservation.manager.service;

import com.reservation.manager.dto.ReservationRequestDto;
import com.reservation.manager.dto.ReservationResponseDto;
import org.springframework.security.core.Authentication;

public interface IReservationService {
    ReservationResponseDto adminReserve(ReservationRequestDto dto, Authentication authentication, Long userId, Long rentalUnitId ) throws Exception;
    ReservationResponseDto userReserve(ReservationRequestDto dto, Authentication authentication, Long id) throws Exception;
    ReservationResponseDto getById(Long id, Authentication authentication) throws Exception;
    ReservationResponseDto update(ReservationRequestDto dto, Long id, Long userId, Authentication authentication) throws Exception;
    String removeReservation(Long id, Authentication authentication) throws Exception;
    String confirmReservation(Long id, Authentication authentication) throws Exception;
}