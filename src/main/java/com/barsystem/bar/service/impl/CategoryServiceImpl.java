package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Category;
import com.barsystem.bar.repository.CategoryRepository;
import com.barsystem.bar.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(@NonNull Category category) {
        try{
            return categoryRepository.save(category);
        }catch(DataIntegrityViolationException ex){
            throw new DuplicateRecordException(
                    String.format("Category with name: %s already exist",category.getName()));
        }
    }

    @Override
    public Set<Category> findAll() {
        return new HashSet<>(categoryRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void delete(String name) {
        categoryRepository.deleteByName(name);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RecordNotFoundException(
                String.format("Category with id:%s is not found",id)));
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(()->new RecordNotFoundException(
                String.format("Category with name:%s is not found",name)));
    }

    @Override
    public Category update(@NonNull Category updated,@NonNull Long id) {
        Category dbCategory = findById(id);
//        Category updatedCategory = Category.builder()
//                .id(dbCategory.getId())
//                .name(updated.getName())
//                .build();
        dbCategory.setName(updated.getName());
        return dbCategory; // return categoryRepository.save(updatedCategory);
    }
}
