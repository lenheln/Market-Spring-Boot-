package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByName(String theRoleName);
}
