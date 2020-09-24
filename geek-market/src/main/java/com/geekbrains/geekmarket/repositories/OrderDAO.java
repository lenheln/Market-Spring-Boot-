package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Long> {

}
