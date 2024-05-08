package com.example.webpos.products.service;

import com.example.webpos.products.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> products() {
        return null;
    }

    @Override
    public Product getProduct(String id) {
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }
}
