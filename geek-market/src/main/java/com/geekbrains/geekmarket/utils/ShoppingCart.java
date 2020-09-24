package com.geekbrains.geekmarket.utils;

import com.geekbrains.geekmarket.entities.Items;
import com.geekbrains.geekmarket.entities.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//@Data
public class ShoppingCart {

    private List<Items> items;
    private double totalCost;

    public ShoppingCart() {
        items = new ArrayList<>();
        totalCost = 0.0d;
    }

    public void addProduct(Product product){
        Items item = findItemByProduct(product);
        if(item == null){
            item = new Items();
            item.setProduct(product);
            item.setItemPrice(product.getPrice());
            item.setQuantity(0L);
            item.setId(0L);
            item.setTotalPrice(0.0);
            items.add(item);
        }
        item.setQuantity(item.getQuantity() + 1);
        recalculate();
    }

    private void recalculate(){
        totalCost = 0.0d;
        for (Items item : items) {
            totalCost += item.getQuantity() * item.getProduct().getPrice();
        }
    }

    private Items findItemByProduct(Product product){
       return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }

    public void setQuantity(Product product, Long quantity){
        Items item = findItemByProduct(product);
        if(item == null) {
            return;
        }
        item.setQuantity(quantity);
        recalculate();
    }

    public void remove(Product product){
        Items item = findItemByProduct(product);
        if(item == null) return;
        items.remove(item);
        recalculate();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public List<Items> getItems() {
        return items;
    }
}
