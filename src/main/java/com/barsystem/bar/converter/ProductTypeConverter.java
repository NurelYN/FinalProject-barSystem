package com.barsystem.bar.converter;

import com.barsystem.bar.dto.product.ProductResponse;
import com.barsystem.bar.dto.productType.ProductTypeRequest;
import com.barsystem.bar.dto.productType.ProductTypeResponse;
import com.barsystem.bar.model.ProductType;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeConverter {

    public ProductType convert(ProductTypeRequest productTypeRequest){
        return ProductType.builder()
                .name(productTypeRequest.getName())
                .build();
    }

    public ProductTypeResponse convert(ProductType productType){
        return ProductTypeResponse.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }
}
