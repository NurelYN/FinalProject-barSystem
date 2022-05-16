package com.barsystem.bar.dto.product;

import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.model.UserType;
import lombok.Builder;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;

@Data
@Builder
public class ProductResponse {

    private Long id;

    private String subtype;

    private String brand;

    private double price;

    private int quantity;

    private ProductType type;
}
