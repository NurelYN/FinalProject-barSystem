package com.barsystem.bar.converter;

import com.barsystem.bar.dto.category.CategoryRequest;
import com.barsystem.bar.dto.category.CategoryResponse;
import com.barsystem.bar.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public Category convert(CategoryRequest categoryRequest){
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryResponse convert(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
