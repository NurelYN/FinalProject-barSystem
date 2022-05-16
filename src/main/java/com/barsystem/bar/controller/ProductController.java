package com.barsystem.bar.controller;

import com.barsystem.bar.converter.ProductConverter;
import com.barsystem.bar.dto.product.ProductRequest;
import com.barsystem.bar.dto.product.ProductResponse;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value="products")
public class ProductController {

    private final ProductService productService;

    private final ProductConverter productConverter;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest productRequest){
        Product product = productConverter.convert(productRequest);
        Product savedProduct = productService.save(product);
        ProductResponse productResponse = productConverter.convert(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<Set<ProductResponse>> findAll(){
        return ResponseEntity.ok().body(productService.findAll()
                .stream()
                .map(productConverter::convert)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        ProductResponse response = productConverter.convert(product);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductRequest productRequest,@PathVariable Long id){
        Product product = productConverter.convert(productRequest);
        Product updatedProduct = productService.update(product,id);
        ProductResponse productResponse =productConverter.convert(updatedProduct);
        return ResponseEntity.ok().body(productResponse);
    }
}
