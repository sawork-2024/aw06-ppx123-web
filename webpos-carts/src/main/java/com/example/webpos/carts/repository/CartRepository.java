package com.example.webpos.carts.repository;

import com.example.webpos.carts.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
}
