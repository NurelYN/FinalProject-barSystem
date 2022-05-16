package com.barsystem.bar.controller;

import com.barsystem.bar.converter.ProductTypeConverter;
import com.barsystem.bar.dto.productType.ProductTypeRequest;
import com.barsystem.bar.dto.productType.ProductTypeResponse;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.service.ProductTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value=ProductTypeController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductTypeControllerTest extends BaseControllerTest{

    @MockBean
    private ProductTypeService productTypeService;

    @MockBean
    private ProductTypeConverter productTypeConverter;

    @Test
    public void save() throws Exception {
        ProductTypeRequest productTypeRequest = ProductTypeRequest.builder()
                .name("anyType")
                .build();

        String reqJson = objectMapper.writeValueAsString(productTypeRequest);

        when(productTypeConverter.convert(any(ProductTypeRequest.class)))
                .thenReturn(ProductType.builder().build());
        when(productTypeService.save(any(ProductType.class)))
                .thenReturn(ProductType.builder().build());
        when(productTypeConverter.convert(any(ProductType.class)))
                .thenReturn(ProductTypeResponse.builder()
                        .id(1L)
                        .name("anyType")
                        .build());

        mockMvc.perform(post("/productTypes")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("anyType")));
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/productTypes"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        when(productTypeService.findById(any(Long.class)))
                .thenReturn(ProductType.builder().build());
        when(productTypeConverter.convert(any(ProductType.class)))
                .thenReturn(ProductTypeResponse.builder()
                        .id(1L)
                        .name("anyName")
                        .build());

        mockMvc.perform(get("/productTypes/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("anyName")));
    }

    @Test
    public void deleteHappy() throws Exception {
        mockMvc.perform(delete("/productTypes/id/1"))
                .andExpect(status().isOk());
    }
}
