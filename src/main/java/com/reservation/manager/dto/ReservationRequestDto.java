package com.reservation.manager.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {

    @NotNull(message = "Amount of people is required")
    @Column(name = "amount_of_people")
    @Min(1)
    @Max(9)
    private int amountOfPeople;

    @NotNull(message = "Check-in date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate checkOut;

    @NotNull(message = "Cost per night is required")
    @Column(name = "cost_per_night")
    @Min(0)
    private Double costPerNight;

    @NotNull(message = "Percent is required")
    @Min(0)
    @Max(value = 100, message = "percent max value is 100")
    private int percent;

}
