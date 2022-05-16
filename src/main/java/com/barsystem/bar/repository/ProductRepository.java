package com.barsystem.bar.repository;

import com.barsystem.bar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    void deleteByBrand(String brand);

    Optional<Product> findByBrand(String brand);
}
