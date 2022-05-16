package com.barsystem.bar.dto.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TableResponse {

    private Long id;
    private Integer number;
}
