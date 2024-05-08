package com.example.webpos.products.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@Data
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private double price;
    private String img;

    private String _id;
    private String category;
    private int quantity;
    private int stock;

    public Product() {

    }


    public Product(String id, String title, double v, String img) {
        this.id = id;
        this.name = title;
        this.price = v;
        this.img = img;
    }


}
