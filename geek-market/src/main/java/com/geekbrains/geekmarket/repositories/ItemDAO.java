package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO extends JpaRepository<Items, Long> {

}
