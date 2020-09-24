package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findById(int id);
    List<Product> findAll();

    //По максимальной цене
    List<Product> findByPriceLessThan(int price);

    //По минимальной цене
    List<Product> findByPriceGreaterThan(int price);

    //В промежутке
    List<Product> findByPriceBetween(int minPrice, int maxPrice);
}
