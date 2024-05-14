package com.example.webpos.carts.service;

import com.example.webpos.carts.model.Cart;
import com.example.webpos.carts.model.Item;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Double checkout(Cart cart);

    Cart addCart(Cart cart);

    Cart addItemToCart(Integer cartId, Item item);

    List<Cart> getAllCarts();

    Optional<Cart> getCart(Integer cartId);

}
