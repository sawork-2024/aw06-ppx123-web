package com.example.webpos.products.rest;

import com.example.webpos.products.service.ProductService;
import com.example.webpos.products.mapper.ProductMapper;
import com.example.webpos.products.model.Product;
import com.example.webpos.api.rest.api.ProductApi;
import com.example.webpos.api.rest.model.InlineObjectDto;
import com.example.webpos.api.rest.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type", origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/")
public class ProductsController implements ProductApi {
    private ProductService productService;

    private ProductMapper productMapper;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ResponseEntity<List<ProductDto>> listProducts() {
        return ResponseEntity.ok(productMapper.toProductDtos(productService.products()));
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(String productId) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.getProduct(productId)));
    }

    @Override
    public ResponseEntity<List<ProductDto>> patchProducts(String productId, InlineObjectDto inlineObjectDto) {
        productService.getProduct(productId).setQuantity(inlineObjectDto.getQuantity());
        Product p = productService.getProduct(productId);
        p.setQuantity(inlineObjectDto.getQuantity());
        productService.updateProduct(p);
        return ResponseEntity.ok(productMapper.toProductDtos(productService.products()));
    }


}
