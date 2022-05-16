package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.repository.ProductTypeRepository;
import com.barsystem.bar.service.ProductTypeService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public ProductType save(@NonNull ProductType productType) {
        try{
            return productTypeRepository.save(productType);
        } catch(DataIntegrityViolationException ex) {
            throw new DuplicateRecordException(
                    String.format("Product type with name:%s already exist",productType.getName()));
        }
    }

    @Override
    public Set<ProductType> findAll() {
        return new HashSet<>(productTypeRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        productTypeRepository.deleteById(id);
    }

    @Override
    public void delete(String name) {
        productTypeRepository.deleteByName(name);
    }

    @Override
    public ProductType findById(Long id) {
        return productTypeRepository.findById(id).orElseThrow(()->new RecordNotFoundException(
                String.format("Product type with id:%s is not found",id)));
    }

    @Override
    public ProductType findByName(String name) {
        return productTypeRepository.findByName(name).orElseThrow(()->new RecordNotFoundException(
                String.format("Product type with name:%s is not found",name)));
    }

    @Override
    public ProductType update(@NonNull ProductType updated,@NonNull Long id) {
        ProductType dbProductType = findById(id);
//        ProductType updatedProductType = ProductType.builder()
//                .id(dbProductType.getId())
//                .name(updated.getName())
//                .build();
//        return productTypeRepository.save(updatedProductType);
        dbProductType.setName(updated.getName());
        return dbProductType;
    }
}
