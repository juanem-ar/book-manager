package com.reservation.manager.repository;

import com.reservation.manager.Enum.ERoles;
import com.reservation.manager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(ERoles name);
}
