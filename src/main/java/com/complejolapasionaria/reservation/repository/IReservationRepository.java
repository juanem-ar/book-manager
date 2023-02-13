package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {
}
