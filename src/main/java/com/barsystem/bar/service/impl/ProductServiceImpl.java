package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.repository.ProductRepository;
import com.barsystem.bar.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(@NonNull Product product) {
            return productRepository.save(product);
    }

    @Override
    public Set<Product> findAll() {
        return new HashSet<>(productRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void delete(String brand) {
        productRepository.deleteByBrand(brand);
    }

    @Override
    public Product findById(Long id) {
       return productRepository.findById(id).orElseThrow(()->new RecordNotFoundException(
               String.format("Product with id:%s is not found",id)));
    }

    @Override
    public Product findByBrand(String brand) {
        return productRepository.findByBrand(brand).orElseThrow(()->new RecordNotFoundException(
                String.format("Product with brand:%s is not found",brand)));
    }

    @Override
    public Product update(@NonNull Product updated,@NonNull Long id) {

        Product dbProduct = findById(id);
//        Product updatedProduct = Product.builder()
//                .id(dbProduct.getId())
//                .subtype(updated.getSubtype())
//                .brand(updated.getBrand())
//                .price(updated.getPrice())
//                .quantity(updated.getQuantity())
//                .type(updated.getType())
//                .build();
//        return productRepository.save(updatedProduct);
        dbProduct.setSubtype(updated.getSubtype());
        dbProduct.setBrand(updated.getBrand());
        dbProduct.setPrice(updated.getPrice());
        dbProduct.setQuantity(updated.getQuantity());
        dbProduct.setType(updated.getType());

        return dbProduct;
    }
}
