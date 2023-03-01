package com.reservation.manager.dto;

import com.reservation.manager.Enum.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return "Reservation: [" +
                "id: " + this.id + "\n" +
                "user: " + this.fullName + "\n" +
                "phone: " + this.phone + "\n" +
                "deleted: " + this.deleted + "\n" +
                "creation date: " + this.creationDate + "\n" +
                "update date: " + this.updateDate + "\n" +
                "amount of people: " + this.amountOfPeople + "\n" +
                "check-i date: " + this.checkIn + "\n" +
                "check-out date: " + this.checkOut + "\n" +
                "unit name: " + this.unitName + "\n" +
                "cost per night: " + this.costPerNight + "\n" +
                "partial payment: " + this.partialPayment + "\n" +
                "payment percent: " + this.percent + "\n" +
                "debit amount: " + this.debit + "\n" +
                "total amount: " + this.totalAmount + "\n" +
                "reservation status: " + this.status +
                "]";
    }
}
