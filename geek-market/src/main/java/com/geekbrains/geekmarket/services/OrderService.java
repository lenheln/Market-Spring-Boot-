package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.Orders;
import com.geekbrains.geekmarket.repositories.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private OrderDAO orderDAO;
    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Transactional
    public void saveOrder(Orders order) {
       orderDAO.save(order);
    }
}
