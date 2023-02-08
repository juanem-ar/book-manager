package com.complejolapasionaria.reservation.model;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
@SQLDelete(sql = "UPDATE reservations SET deleted = true WHERE id=?")
public class Reservation  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "user_id", updatable = false)
    @JsonIgnore
    private User user;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @NotNull(message = "Amount of people is required")
    @Column(name = "amount_of_people")
    @Size(min = 1, max = 9)
    private int amountOfPeople;

    @NotNull(message = "Check-in date is required (YYYY-MM-dd).")
    @Column(name = "check_in")
    @JsonFormat(pattern = "YYYY-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required (YYYY-MM-dd).")
    @Column(name = "check_out")
    @JsonFormat(pattern = "YYYY-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate checkOut;

    @NotNull(message = "Cost per night is required")
    @Column(name = "cost_per_night")
    private Double costPerNight;

    @NotNull(message = "Partial payment is required")
    @Column(name = "partial_payment")
    private Double partialPayment;

    private Double debit;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private EStatus status;
}
