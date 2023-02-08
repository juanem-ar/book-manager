package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.CommerceBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommerceBuildingRepository extends JpaRepository<CommerceBuilding,Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
}
