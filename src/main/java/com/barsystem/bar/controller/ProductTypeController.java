package com.barsystem.bar.controller;

import com.barsystem.bar.converter.ProductTypeConverter;
import com.barsystem.bar.dto.productType.ProductTypeRequest;
import com.barsystem.bar.dto.productType.ProductTypeResponse;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.service.ProductTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value="/productTypes")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    private final ProductTypeConverter productTypeConverter;

    @PostMapping
    public ResponseEntity<ProductTypeResponse> save(@RequestBody @Valid ProductTypeRequest productTypeRequest) {
        ProductType productType = productTypeConverter.convert(productTypeRequest);
        ProductType savedProductType = productTypeService.save(productType);
        ProductTypeResponse productTypeResponse = productTypeConverter.convert(savedProductType);
        return ResponseEntity.status(HttpStatus.CREATED).body(productTypeResponse);
    }

    @GetMapping
    public ResponseEntity<Set<ProductTypeResponse>> findAll() {
        return ResponseEntity.ok().body(productTypeService.findAll()
                .stream()
                .map(productTypeConverter::convert)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ProductTypeResponse> findById(@PathVariable Long id) {
        ProductType productType = productTypeService.findById(id);
        ProductTypeResponse response = productTypeConverter.convert(productType);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        productTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
