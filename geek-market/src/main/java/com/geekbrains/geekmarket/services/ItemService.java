package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.Items;
import com.geekbrains.geekmarket.repositories.ItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {
    private ItemDAO itemDAO;

    @Autowired
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Transactional
    public void saveItem(Items orderItem) {
        itemDAO.save(orderItem);
    }
}
