package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Table;
import com.barsystem.bar.repository.TableRepository;
import com.barsystem.bar.service.TableService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Override
    public Table save(@NonNull Table table) {
        try{
           return tableRepository.save(table);
        }catch(DataIntegrityViolationException ex){
            throw new DuplicateRecordException(
                    String.format("Table with number :%s already exist",table.getNumber()));
        }
    }

    @Override
    public Set<Table> findAll() {
        return new HashSet<>(tableRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        tableRepository.deleteById(id);
    }

    @Override
    public Table findById(Long id) {
        return tableRepository.findById(id).orElseThrow(()->new RecordNotFoundException(
                String.format("Table with id:%s is not found",id)));
    }
}
