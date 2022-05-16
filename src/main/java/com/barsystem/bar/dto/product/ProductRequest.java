package com.barsystem.bar.dto.product;

import com.barsystem.bar.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String subtype;

    private String brand;

    private double price;

    private int quantity;

    private String type;
}
