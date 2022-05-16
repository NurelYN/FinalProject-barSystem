package com.barsystem.bar.dto.productType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinitions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTypeRequest {

    private String name;
}
