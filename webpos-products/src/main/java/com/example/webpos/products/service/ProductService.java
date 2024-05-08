package com.example.webpos.products.service;

import com.example.webpos.products.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {

    public List<Product> products();
    public Product getProduct(String id);

    public void updateProduct(Product product);
}
