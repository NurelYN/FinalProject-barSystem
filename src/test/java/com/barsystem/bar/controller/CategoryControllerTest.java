package com.barsystem.bar.controller;

import com.barsystem.bar.converter.CategoryConverter;
import com.barsystem.bar.dto.category.CategoryRequest;
import com.barsystem.bar.dto.category.CategoryResponse;
import com.barsystem.bar.model.Category;
import com.barsystem.bar.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value=CategoryController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CategoryControllerTest extends  BaseControllerTest{

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryConverter categoryConverter;

    @Test
    public void save() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("FOOD")
                .build();

        String reqJson = objectMapper.writeValueAsString(categoryRequest);

        when(categoryConverter.convert(any(CategoryRequest.class)))
                .thenReturn(Category.builder().build());
        when(categoryService.save(any(Category.class)))
                .thenReturn(Category.builder().build());
        when(categoryConverter.convert(any(Category.class)))
                .thenReturn(CategoryResponse.builder()
                        .id(1L)
                        .name("FOOD")
                        .build());

        mockMvc.perform(post("/categories")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("FOOD")));
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void findById() throws Exception {
        when(categoryService.findByName(any(String.class)))
                .thenReturn(Category.builder().build());
        when(categoryConverter.convert(any(Category.class)))
                .thenReturn(CategoryResponse.builder()
                        .id(1L)
                        .name("FOOD")
                        .build());

        mockMvc.perform(get("/categories/name/FOOD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("FOOD")));
    }

    @Test
    public void deleteHappy() throws Exception {
        mockMvc.perform(delete("/categories/name/FOOD"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateHappy() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("DRINKS")
                .build();
        String reqJson=objectMapper.writeValueAsString(categoryRequest);
        when(categoryConverter.convert(any(CategoryRequest.class)))
                .thenReturn(Category.builder().build());
        when(categoryService.update(any(Category.class),any(Long.class)))
                .thenReturn(Category.builder().build());
        when(categoryConverter.convert(any(Category.class)))
                .thenReturn(CategoryResponse.builder()
                        .id(1L)
                        .name("UPDATED_DRINKS")
                        .build());
        mockMvc.perform(put("/categories/id/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("UPDATED_DRINKS")));
    }

}
