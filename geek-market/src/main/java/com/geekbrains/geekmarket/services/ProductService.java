package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.Product;
import com.geekbrains.geekmarket.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductDAO productDAO;
    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Transactional(readOnly = true)
    public Product findProductById(int id){
        return productDAO.findById(id);
    }

    @Transactional
    public Product getProductById(Long id){
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Transactional
    public Product saveOrUpdate(Product product){
        return productDAO.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProducts(){
        return productDAO.findAll();
    }

    @Transactional(readOnly = true)
    public Product findProductByName(String name){
        return productDAO.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Product> findByMaxPrice(int price){
        return productDAO.findByPriceLessThan(price);
    }

    @Transactional(readOnly = true)
    public List<Product> findByMinPrice(int price){
        return productDAO.findByPriceGreaterThan(price);
    }

    @Transactional(readOnly = true)
    public List<Product> findByPriceBetween(int minPrice, int maxPrice){
        return productDAO.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllPage(int page){
        Page<Product> p = productDAO.findAll(PageRequest.of(page,5));
        return p.stream().collect(Collectors.toList());
    }

    @Transactional
    public void addProduct(Product product){
        productDAO.save(product);
    }
}
