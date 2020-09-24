package com.geekbrains.geekmarket.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    int id;

//    @OneToMany(mappedBy = "order")
//    List<Items> itemsList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
//
//    public List<Items> getItemsList() {
//        return itemsList;
//    }
//
//    public void setItemsList(List<Items> itemsList) {
//        this.itemsList = itemsList;
//    }
}
