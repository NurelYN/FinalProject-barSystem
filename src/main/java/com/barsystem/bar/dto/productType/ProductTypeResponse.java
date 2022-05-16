package com.barsystem.bar.dto.productType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

@Data
@AllArgsConstructor
@Builder
public class ProductTypeResponse {

    private Long id;

    private String name;
}
