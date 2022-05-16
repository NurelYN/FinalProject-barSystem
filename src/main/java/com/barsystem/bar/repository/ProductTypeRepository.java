package com.barsystem.bar.repository;

import com.barsystem.bar.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {

    void deleteByName(String name);

    Optional<ProductType> findByName(String name);
}
