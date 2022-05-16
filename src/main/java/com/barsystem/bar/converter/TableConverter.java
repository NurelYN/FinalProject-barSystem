package com.barsystem.bar.converter;

import com.barsystem.bar.dto.table.TableRequest;
import com.barsystem.bar.dto.table.TableResponse;
import com.barsystem.bar.model.Table;
import org.springframework.stereotype.Component;



@Component
public class TableConverter {

    public Table convert(TableRequest tableRequest){
        return Table.builder()
                .number(tableRequest.getNumber())
                .build();
    }

    public TableResponse convert(Table table){
        return TableResponse.
                builder()
                .id(table.getId())
                .number(table.getNumber())
                .build();
    }

}
