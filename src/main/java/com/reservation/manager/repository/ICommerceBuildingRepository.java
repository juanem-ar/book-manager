package com.reservation.manager.repository;

import com.reservation.manager.model.CommerceBuilding;
import com.reservation.manager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommerceBuildingRepository extends JpaRepository<CommerceBuilding,Long> {
    boolean existsByName(String name);
    boolean existsByIdAndOwner(Long id, User user);
    boolean existsById(Long id);
    Page<CommerceBuilding> findAllByDeleted(boolean deleted, Pageable page);
    CommerceBuilding getReferenceByIdAndOwner(Long id, User user);
}
