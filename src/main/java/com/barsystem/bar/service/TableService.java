package com.barsystem.bar.service;

import com.barsystem.bar.model.Table;

import java.util.Set;

public interface TableService {

    Table save(Table table);

    Set<Table> findAll();

    void delete(Long id);

    Table findById(Long id);
}
