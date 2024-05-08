package com.example.webpos.counter.rest;


import com.example.webpos.api.rest.api.CounterApi;
import com.example.webpos.api.rest.model.CartDto;
import com.example.webpos.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController implements CounterApi {

    private CounterService counterService;

    @Autowired
    public void setCounterService(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public ResponseEntity<Double> checkout(CartDto cart) {
        return ResponseEntity.ok(counterService.checkout(cart));
    }

}
