package com.example.webpos.counter.service;

import com.example.webpos.api.rest.model.CartDto;
import com.example.webpos.api.rest.model.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    public double checkout(CartDto cart) {
        double ret = 0;
        for (CartItemDto item: cart.getItems()) {
            ret += item.getAmount() * item.getProduct().getPrice();
        }
        return ret;
    }
}
