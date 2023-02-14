package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.model.User;
import org.springframework.security.core.Authentication;

public interface IReservationService {
    ReservationResponseDto adminReserve(ReservationRequestDto dto,Authentication authentication, Long userId, Long id) throws Exception;
    ReservationResponseDto userReserve(ReservationRequestDto dto, Authentication authentication, Long id) throws Exception;
    ReservationResponseDto reservationSave(ReservationRequestDto dto, User user, Long id) throws Exception;
}
