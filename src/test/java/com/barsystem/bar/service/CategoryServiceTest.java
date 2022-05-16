package com.barsystem.bar.service;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Category;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.repository.CategoryRepository;
import com.barsystem.bar.service.impl.CategoryServiceImpl;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    @BeforeEach
    public void setUp(){
        categoryServiceImpl = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void verifySave(){
        Category category = Category.builder().build();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        categoryServiceImpl.save(category);
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    public void verifySaveNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            categoryServiceImpl.save(null);
        });
    }

    @Test
    public void verifySaveThrowsDuplicateRecordException(){
        Category category = Category.builder()
                .name("FOOD")
                .build();

        String expectedMessage = "Category with name: FOOD already exist";
        when(categoryServiceImpl.save(category))
                .thenThrow(DataIntegrityViolationException.class);

        DuplicateRecordException duplicateRecordException = assertThrows(DuplicateRecordException.class,
                () -> categoryServiceImpl.save(category));

        assertEquals(expectedMessage,duplicateRecordException.getMessage());
    }

    @Test
    public void verifyFindAll(){
        categoryServiceImpl.findAll();
        verify(categoryRepository,times(1)).findAll();
    }

    @Test
    public void verifyFindById(){
        Long id = 5L;
        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(Category.builder()
                        .id(5L)
                        .build()));
        Category actualCategory = categoryServiceImpl.findById(id);
        assertEquals(id,actualCategory.getId());
        verify(categoryRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage="Category with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            categoryServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindByName(){
        String name = "FOOD";
        when(categoryRepository.findByName(name))
                .thenReturn(Optional.of(Category.builder()
                        .name("FOOD")
                        .build()));
        Category actualCategory = categoryServiceImpl.findByName(name);
        assertEquals(name,actualCategory.getName());
        verify(categoryRepository,times(1)).findByName(name);
    }

    @Test
    public void verifyFindByNameRecordNotFoundException(){
        String expectedMessage="Category with name:FOOD is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            categoryServiceImpl.findByName("FOOD");
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        categoryServiceImpl.delete(id);

        verify(categoryRepository,times(1)).deleteById(id);
    }

    @Test
    public void verifyDeleteByName() {
        String name = "FOOOD";
        categoryServiceImpl.delete(name);

        verify(categoryRepository,times(1)).deleteByName(name);
    }

    @Test
    public void verifyUpdateSucces(){
        Category expectedCategory = Category.builder()
                .id(2L)
                .name("FOOD")
                .build();
        when(categoryRepository.findById(expectedCategory.getId()))
                .thenReturn(Optional.of(Category.builder()
                        .id(expectedCategory.getId())
                        .name("DRINKS")
                        .build()));
        // !
        Category actualResult = categoryServiceImpl.update(expectedCategory,expectedCategory.getId());

        verify(categoryRepository,times(1)).findById(expectedCategory.getId());
        assertEquals(expectedCategory.getName(),actualResult.getName());
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenIdIsNull(){
        assertThrows(NullPointerException.class,()->{
            categoryServiceImpl.update(Category.builder().build(),null);
        });
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenUpdateUserIsNull(){
        assertThrows(NullPointerException.class,()->{
            categoryServiceImpl.update(null,5L);
        });
    }
}
