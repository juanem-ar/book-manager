package com.reservation.manager.dto;

import com.reservation.manager.Enum.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class ReservationResponseWADto {
    private Long id;
    private String unitName;
    private String fullName;
    private String phone;
    private Boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    private int amountOfPeople;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double costPerNight;
    private Double partialPayment;
    private int percent;
    private Double debit;
    private Double totalAmount;

    private EStatus status;

    @Override
    public String toString() {
        return "RESERVATION ID: " + this.id + "\n" +
                "Unit name: " + this.unitName + "\n" +
                "Amount of people: " + this.amountOfPeople + "\n" +
                "Check-in: " + this.checkIn + "\n" +
                "check-out: " + this.checkOut + "\n" + "\n"+

                "USER INFORMATION: " + "\n" +
                "Name: " + this.fullName + "\n" +
                "Phone: " + this.phone + "\n" + "\n" +

                "COST DETAIL: " + "\n" +
                "Cost per night: " + this.costPerNight + "\n" +
                "Partial payment: " + this.partialPayment + "\n" +
                "Payment percent: %" + this.percent + "\n" +
                "Debit: " + this.debit + "\n" +
                "Total: " + this.totalAmount;

    }
}
