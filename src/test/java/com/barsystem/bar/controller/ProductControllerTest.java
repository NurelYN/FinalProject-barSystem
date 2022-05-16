package com.barsystem.bar.controller;

import com.barsystem.bar.converter.ProductConverter;
import com.barsystem.bar.dto.product.ProductRequest;
import com.barsystem.bar.dto.product.ProductResponse;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value=ProductController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductControllerTest extends BaseControllerTest{

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductConverter productConverter;

    @Test
    public void save() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .brand("anyBrand")
                .type("anyType")
                .subtype("anySubType")
                .quantity(1)
                .price(5.0)
                .build();
        String reqJson = objectMapper.writeValueAsString(productRequest);

        when(productConverter.convert(any(ProductRequest.class)))
                .thenReturn(Product.builder().build());
        when(productService.save(any(Product.class)))
                .thenReturn(Product.builder().build());
        when(productConverter.convert(any(Product.class)))
                .thenReturn(ProductResponse.builder()
                        .id(1L)
                        .brand("anyBrand")
                        .type(ProductType.builder()
                                .id(1L)
                                .name("anyName")
                                .build())
                        .subtype("anySubType")
                        .quantity(1)
                        .price(5.0)
                        .build());

        mockMvc.perform(post("/products")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.brand",is("anyBrand")))
                .andExpect(jsonPath("$.subtype",is("anySubType")))
                .andExpect(jsonPath("$.price",is(5.0)))
                .andExpect(jsonPath("$.quantity",is(1)));
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        when(productService.findById(any(Long.class)))
                .thenReturn(Product.builder().build());
        when(productConverter.convert(any(Product.class)))
                .thenReturn(ProductResponse.builder()
                        .id(1L)
                        .build());
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void updateHappy() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .brand("anyBrand")
                .type("anyType")
                .subtype("anySubType")
                .quantity(1)
                .price(5.0)
                .build();

        String reqJson = objectMapper.writeValueAsString(productRequest);

        when(productConverter.convert(any(ProductRequest.class)))
                .thenReturn(Product.builder().build());
        when(productService.update(any(Product.class),any(Long.class)))
                .thenReturn(Product.builder().build());
        when(productConverter.convert(any(Product.class)))
                .thenReturn(ProductResponse.builder()
                        .id(1L)
                        .brand("anyBrand_UPDATED")
                        .type(ProductType.builder()
                                .id(1L)
                                .name("anyName")
                                .build())
                        .subtype("anySubType")
                        .quantity(1)
                        .price(5.0)
                        .build());

        mockMvc.perform(put("/products/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.brand",is("anyBrand_UPDATED")))
                .andExpect(jsonPath("$.subtype",is("anySubType")))
                .andExpect(jsonPath("$.price",is(5.0)))
                .andExpect(jsonPath("$.quantity",is(1)));
    }


}
