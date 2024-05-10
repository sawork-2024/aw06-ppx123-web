package com.example.webpos.products.db;

import com.example.webpos.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosDB {
    List<Product> getProducts();

    Product getProduct(String id);
}
