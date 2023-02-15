package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByUnitId(Long id);

    boolean existsByCheckInLessThanAndCheckOutGreaterThanAndUnitId(LocalDate checkIn, LocalDate checkOut, Long id);
}
