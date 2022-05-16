package com.barsystem.bar.service;


import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Table;
import com.barsystem.bar.repository.TableRepository;
import com.barsystem.bar.service.impl.TableServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    private TableServiceImpl tableServiceImpl;

    @BeforeEach
    public void setUp(){
        tableServiceImpl = new TableServiceImpl(tableRepository);
    }

    @Test
    public void verifySave(){
        Table table = Table.builder().build();
        when(tableRepository.save(any(Table.class)))
                .thenReturn(table);
        tableServiceImpl.save(table);
        verify(tableRepository,times(1)).save(table);
    }

    @Test
    public void verifySaveNullPointerException(){
        assertThrows(NullPointerException.class,()->
                tableServiceImpl.save(null));
    }

    @Test
    public void verifySaveDuplicateRecordException(){
        Table table = Table.builder()
                .number(5)
                .build();

        String expectedMessage="Table with number :5 already exist";

        when(tableServiceImpl.save(table))
                .thenThrow(DataIntegrityViolationException.class);

        Throwable ex = assertThrows(DuplicateRecordException.class,
                ()->tableServiceImpl.save(table));

        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindAll(){
        tableServiceImpl.findAll();
        verify(tableRepository,times(1)).findAll();
    }

    @Test
    public void verifyDeleteById(){
        Long id = 2L;
        tableServiceImpl.delete(id);
        verify(tableRepository,times(1)).deleteById(id);
    }

    @Test
    public void verifyFindById(){
       Long id = 5L;
        when(tableRepository.findById(id))
                .thenReturn(Optional.of(Table.builder()
                        .id(5L)
                        .build()));
        Table actualTable=tableServiceImpl.findById(id);
        assertEquals(id,actualTable.getId());
        verify(tableRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage="Table with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            tableServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }
}
