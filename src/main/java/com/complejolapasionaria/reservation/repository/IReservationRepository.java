package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByCheckInLessThanAndCheckOutGreaterThanAndUnitId(LocalDate checkIn, LocalDate checkOut,Long id);
}
