package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserName(String userName);
}
