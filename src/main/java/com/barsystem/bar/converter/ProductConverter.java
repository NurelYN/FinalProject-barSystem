package com.barsystem.bar.converter;

import com.barsystem.bar.dto.product.ProductRequest;
import com.barsystem.bar.dto.product.ProductResponse;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.service.ProductTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductConverter {

    private ProductTypeService productTypeService;

    public Product convert(ProductRequest productRequest){
        return Product.builder()
                .subtype(productRequest.getSubtype())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .type(productTypeService.findByName(productRequest.getType()))
                .build();
    }

    public ProductResponse convert(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .type(product.getType())
                .subtype(product.getSubtype())
                .brand(product.getBrand())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
