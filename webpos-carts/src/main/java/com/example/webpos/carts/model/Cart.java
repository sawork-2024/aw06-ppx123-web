package com.example.webpos.carts.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Accessors(fluent = true, chain = true)
public class Cart {
    @Id
    @GeneratedValue
    private Integer id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        for (Item cur : items) {
            if (cur.id().equals(item.id())) {
                cur.quantity(cur.quantity() + item.quantity());
                return true;
            }
        }
        item.cart(this);
        return items.add(item);
    }

    public void clear() {
        items.clear();
    }


    public boolean removeItem(String productId) {
        List<Item> newItems = new ArrayList<>();
        boolean ret = false;
        for (Item cur : items) {
            if (cur.id().equals(productId)) {
                ret = true;
            } else {
                newItems.add(cur);
            }
        }
        if (ret) {
            items = newItems;
        }
        return ret;
    }
}
