package com.barsystem.bar.service;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.repository.ProductTypeRepository;
import com.barsystem.bar.service.impl.ProductTypeServiceImpl;
import net.bytebuddy.implementation.bytecode.Throw;
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
public class ProductTypeServiceTest {
    @Mock
    private ProductTypeRepository productTypeRepository;

    private ProductTypeServiceImpl productTypeServiceImpl;

    @BeforeEach
    public void setUp(){
        productTypeServiceImpl = new ProductTypeServiceImpl(productTypeRepository);
    }

    @Test
    public void verifySave(){
        ProductType productType = ProductType.builder().build();
        when(productTypeRepository.save(any(ProductType.class)))
                .thenReturn(productType);
        productTypeServiceImpl.save(productType);
        verify(productTypeRepository,times(1)).save(productType);
    }

    @Test
    public void verifySaveNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            productTypeServiceImpl.save(null);
        });
    }

    @Test
    public void verifySaveThrowsDuplicateRecordException(){
        ProductType productType = ProductType.builder()
                .name("ALCOHOL")
                .build();

        String expectedMessage = "Product type with name:ALCOHOL already exist";
        when(productTypeServiceImpl.save(productType))
                .thenThrow(DataIntegrityViolationException.class);

        Throwable ex = assertThrows(DuplicateRecordException.class,()->{
            productTypeServiceImpl.save(productType);
        });

        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindAll(){
        productTypeServiceImpl.findAll();
        verify(productTypeRepository,times(1)).findAll();
    }

    @Test
    public void verifyDeleteById(){
        Long id = 5L;
        productTypeServiceImpl.delete(id);
        verify(productTypeRepository,times(1)).deleteById(id);
    }

    @Test
    public void verifyDeleteByName(){
        String name = "Name";
        productTypeServiceImpl.delete(name);
        verify(productTypeRepository,times(1)).deleteByName(name);
    }

    @Test
    public void verifyFindById(){
        Long id = 5L;
        when(productTypeRepository.findById(id))
                .thenReturn(Optional.of(ProductType.builder()
                        .id(5L)
                        .build()));
        ProductType productType = productTypeServiceImpl.findById(id);
        assertEquals(id,productType.getId());
        verify(productTypeRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByRecordNotFoundException(){
        String expectedMessage = "Product type with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            productTypeServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindByName(){
        String name = "Name";
        when(productTypeRepository.findByName(name))
                .thenReturn(Optional.of(ProductType.builder()
                        .name(name)
                        .build()));
        ProductType productType = productTypeServiceImpl.findByName(name);
        assertEquals(name,productType.getName());
        verify(productTypeRepository,times(1)).findByName(name);
    }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage = "Product type with name:Name is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            productTypeServiceImpl.findByName("Name");
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenIdIsNull(){
        assertThrows(NullPointerException.class,()->{
            productTypeServiceImpl.update(ProductType.builder().build(),null);
        });
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenUpdateUserIsNull(){
        assertThrows(NullPointerException.class,()->{
            productTypeServiceImpl.update(null,5L);
        });
    }

    @Test
    public void verifyUpdate(){
        ProductType expectedProductType = ProductType.builder()
                .id(2L)
                .name("ALCOHOL")
                .build();
        when(productTypeRepository.findById(expectedProductType.getId()))
                .thenReturn(Optional.of(ProductType.builder()
                        .id(expectedProductType.getId())
                        .name("FOOD")
                        .build()));

        ProductType actualResult = productTypeServiceImpl.update(expectedProductType, expectedProductType.getId());

        verify(productTypeRepository,times(1)).findById(expectedProductType.getId());
        assertEquals(expectedProductType.getName(),actualResult.getName());
    }
}
