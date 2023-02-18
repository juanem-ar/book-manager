package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.CommerceBuilding;
import com.complejolapasionaria.reservation.model.RentalUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalUnitRepository extends JpaRepository<RentalUnit,Long> {
    boolean existsByNameAndBuilding(String name, CommerceBuilding id);
    Page<RentalUnit> findAllByDeleted(boolean deleted, Pageable page);
}
