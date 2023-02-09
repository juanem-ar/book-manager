package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.model.CommerceBuilding;
import com.complejolapasionaria.reservation.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommerceBuildingRepository extends JpaRepository<CommerceBuilding,Long> {
    boolean existsByName(String name);
    boolean existsByIdAndOwner(Long id, User user);
    boolean existsById(Long id);
    List<CommerceBuilding> findAllByOwner(User owner);
    Page<CommerceBuilding> findAllByDeleted(boolean deleted, Pageable page);
    CommerceBuilding getReferenceByIdAndOwner(Long id, User user);
}
