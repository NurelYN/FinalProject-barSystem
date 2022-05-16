package com.barsystem.bar.controller;

import com.barsystem.bar.converter.TableConverter;
import com.barsystem.bar.dto.table.TableRequest;
import com.barsystem.bar.dto.table.TableResponse;
import com.barsystem.bar.model.Table;
import com.barsystem.bar.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@RestController
@RequestMapping(value="/tables")
public class TableController {

    private final TableService tableService;

    private final TableConverter tableConverter;

    @PostMapping
    public ResponseEntity<TableResponse> save(@RequestBody @Valid TableRequest tableRequest){
        Table table = tableConverter.convert(tableRequest);
        Table savedTable = tableService.save(table);
        TableResponse responseTable = tableConverter.convert(savedTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTable);
    }

    @GetMapping
    public ResponseEntity<Set<TableResponse>> findAll(){
        return ResponseEntity.ok().body(tableService.findAll()
                .stream()
                .map(tableConverter::convert)
                .collect(Collectors.toSet()));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        tableService.delete(id);
        return  ResponseEntity.ok().build();
    }
}
