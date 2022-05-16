package com.barsystem.bar.service;

import com.barsystem.bar.model.Category;

import java.util.Set;

public interface CategoryService {

    Category save(Category category);

    Set<Category> findAll();

    void delete(Long id);

    void delete(String name);

    Category findById(Long id);

    Category findByName(String name);

    Category update(Category updated, Long id);
}
