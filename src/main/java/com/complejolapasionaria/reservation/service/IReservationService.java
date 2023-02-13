package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import org.springframework.security.core.Authentication;

public interface IReservationService {
    ReservationResponseDto saveReservation(ReservationRequestDto dto, Authentication authentication) throws Exception;
}
