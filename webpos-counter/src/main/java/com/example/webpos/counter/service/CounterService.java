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
        double discount = 0;
        for (int i = 1;i < ret * 10000000; i++) {
            discount += i;
        }
        ret -= discount / 1000 / 1000 / 1000 / 10000;
        return ret;
    }
}
