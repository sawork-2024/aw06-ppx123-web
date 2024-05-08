package com.example.webpos.carts.rest;


import com.example.webpos.api.rest.api.CartsApi;
import com.example.webpos.api.rest.model.CartDto;
import com.example.webpos.api.rest.model.CartItemDto;
import com.example.webpos.carts.mapper.CartMapper;
import com.example.webpos.carts.model.Cart;
import com.example.webpos.carts.model.Item;
import com.example.webpos.carts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type", origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/")
public class CartController implements CartsApi {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        Optional<Cart> cart = cartService.getCart(cartId);
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Item item = cartMapper.toItem(cartItemDto, cartMapper.toCartDto(cart.get()));
        Cart ret = cartService.addItemToCart(cartId, item);
        if (ret == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toCartDto(ret));
    }

    @Override
    public ResponseEntity<List<CartDto>> listCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(cartMapper.toCartDtos(carts));
    }

    @Override
    public ResponseEntity<CartDto> getCartById(Integer cartId) {
        Optional<Cart> cart = cartService.getCart(cartId);
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toCartDto(cart.get()));
    }

    @Override
    public ResponseEntity<CartDto> createCart(CartDto cartDto) {
        Cart cart = cartService.addCart();
        return ResponseEntity.ok(cartMapper.toCartDto(cart));
    }


}
