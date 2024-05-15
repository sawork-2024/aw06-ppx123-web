package com.example.webpos.carts.service;

import com.example.webpos.api.rest.model.CartDto;
import com.example.webpos.carts.mapper.CartMapper;
import com.example.webpos.carts.model.Cart;
import com.example.webpos.carts.model.Item;
import com.example.webpos.carts.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String baseurl = "http://webpos-gateway:8080/";

    @Override
    public Double checkout(Cart cart) {
        CartDto cartDto = cartMapper.toCartDto(cart);
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
            String json = mapper.writeValueAsString(cartDto);
            String url = baseurl + "counter/checkout";
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            return restTemplate.postForObject(url, request, Double.class);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart addItemToCart(Integer cartId, Item item) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            return null;
        }
        cart.get().addItem(item);
        cartRepository.save(cart.get());
        return cart.get();
    }

    @Override
    public List<Cart> getAllCarts() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Optional<Cart> getCart(Integer cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Cart addCart(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

}
