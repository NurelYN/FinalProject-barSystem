package com.barsystem.bar.service;

import com.barsystem.bar.model.ProductType;


import java.util.Set;

public interface ProductTypeService {

    ProductType save(ProductType productType);

    Set<ProductType> findAll();

    void delete(Long id);

    void delete(String name);

    ProductType findById(Long id);

    ProductType findByName(String name);

    ProductType update(ProductType updated, Long id);
}
