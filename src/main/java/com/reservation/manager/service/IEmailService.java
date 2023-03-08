package com.reservation.manager.service;

import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.model.Reservation;

import java.io.IOException;

public interface IEmailService {
    void sendWelcomeEmailTo(String to) throws IOException;
    void sendReservationCreatedEmailTo(String to, ReservationResponseDto reservation) throws IOException;
    void sendReservationConfirmEmailTo(String to, Reservation reservation, String paymentMethod) throws IOException;
}
