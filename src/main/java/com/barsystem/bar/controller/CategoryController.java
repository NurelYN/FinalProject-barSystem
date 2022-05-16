package com.barsystem.bar.controller;

import com.barsystem.bar.converter.CategoryConverter;
import com.barsystem.bar.dto.category.CategoryRequest;
import com.barsystem.bar.dto.category.CategoryResponse;
import com.barsystem.bar.model.Category;
import com.barsystem.bar.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value="/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryConverter categoryConverter;

    @PostMapping
    public ResponseEntity<CategoryResponse>save(@RequestBody @Valid CategoryRequest categoryRequest){
        Category category = categoryConverter.convert(categoryRequest);
        Category savedCategory = categoryService.save(category);
        CategoryResponse response =categoryConverter.convert(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Set<CategoryResponse>>findAll(){
        return ResponseEntity.ok().body(categoryService.findAll()
                .stream()
                .map(categoryConverter::convert)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value="/name/{name}")
    public ResponseEntity<CategoryResponse> findByName(@PathVariable String name){
        Category category = categoryService.findByName(name);
        CategoryResponse categoryResponse = categoryConverter.convert(category);
        return ResponseEntity.ok().body(categoryResponse);
    }

    @DeleteMapping(value="/name/{name}")
    public ResponseEntity<HttpStatus> deleteByName(@PathVariable String name){
        categoryService.delete(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="id/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest categoryRequest,@PathVariable Long id){
        Category category = categoryConverter.convert(categoryRequest);
        Category updatedCategory = categoryService.update(category, id);
        CategoryResponse categoryResponse = categoryConverter.convert(updatedCategory);
        return ResponseEntity.ok().body(categoryResponse);
    }
}
