package com.example.webpos.products.service;

import com.example.webpos.products.db.PosDB;
import com.example.webpos.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public Product getProduct(String id) {
        return posDB.getProduct(id);
    }

    @Override
    public void updateProduct(Product product) {
    }
}
