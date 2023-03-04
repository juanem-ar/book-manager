package com.reservation.manager.service;

import com.reservation.manager.dto.ReservationResponseDto;

public interface IWhatsappService {
    void sendWhatsapp(ReservationResponseDto reservation) throws Exception;
}
