package com.complejolapasionaria.reservation.repository;

import com.complejolapasionaria.reservation.Enum.ERoles;
import com.complejolapasionaria.reservation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(ERoles name);
}
