package com.barsystem.bar.service;



import com.barsystem.bar.model.Product;

import java.util.Set;

public interface ProductService {

    Product save(Product product);

    Set<Product> findAll();

    void delete(Long id);

    void delete(String name);

    Product findById(Long id);

    Product findByBrand(String brand);

    Product update(Product updated, Long id);
}

