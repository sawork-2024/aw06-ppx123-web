package com.example.webpos.products.mapper;


import org.mapstruct.Mapper;
import com.example.webpos.api.rest.model.*;
import java.util.List;
import com.example.webpos.products.model.Product;

@Mapper
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtos(List<Product> products);

    Product toProduct(ProductDto productDto);

    List<Product> toProducts(List<ProductDto> productDtos);

}
