package com.barsystem.bar.service;

import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.Product;
import com.barsystem.bar.model.ProductType;
import com.barsystem.bar.repository.ProductRepository;
import com.barsystem.bar.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setUp(){
        productServiceImpl = new ProductServiceImpl(productRepository);
    }

    @Test
    public void verifySave(){
        Product product = Product.builder().build();
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);
        productServiceImpl.save(product);
        verify(productRepository,times(1)).save(product);
    }

    @Test
    public void verifySaveNullPointerException(){
        assertThrows(NullPointerException.class,
                ()->productServiceImpl.save(null));
    }

    @Test
    public void verifyFindAll(){
        productServiceImpl.findAll();
        verify(productRepository,times(1)).findAll();
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        productServiceImpl.delete(id);

        verify(productRepository,times(1)).deleteById(id);
    }

    @Test
    public void verifyDeleteByBrand(){
        String name = "BRAND";
        productServiceImpl.delete(name);
        verify(productRepository,times(1)).deleteByBrand(name);
    }

    @Test
    public void verifyFindById(){
        Long id = 5L;
        when(productRepository.findById(id))
                .thenReturn(Optional.of(Product.builder()
                        .id(5L)
                        .build()));
        Product actualProduct = productServiceImpl.findById(id);
        assertEquals(id,actualProduct.getId());
        verify(productRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage = "Product with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,
                ()->{productServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindByBrand(){
        String brand = "Brand";
        when(productRepository.findByBrand(brand))
                .thenReturn(Optional.of(Product.builder()
                        .brand(brand)
                        .build()));
        Product actualProduct = productServiceImpl.findByBrand(brand);
        assertEquals(brand,actualProduct.getBrand());
        verify(productRepository,times(1)).findByBrand(brand);
    }

    @Test
    public void verifyFindByBrandRecordNotFoundException(){
        String expectedMessage="Product with brand:Fanta is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            productServiceImpl.findByBrand("Fanta");
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenIdIsNull(){
        assertThrows(NullPointerException.class,()->{
            productServiceImpl.update(Product.builder().build(),null);
        });
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenUpdateUserIsNull(){
        assertThrows(NullPointerException.class,()->{
            productServiceImpl.update(null,5L);
        });
    }

    @Test
    public void verifyUpdate(){
        Product expectedProduct = Product.builder()
                .id(2L)
                .type(ProductType.builder()
                        .name("FOOD")
                        .build())
                .subtype("Nuts")
                .brand("Djili")
                .price(2.5)
                .quantity(1)
                .build();
        when(productRepository.findById(expectedProduct.getId()))
                .thenReturn(Optional.of(Product.builder()
                        .id(expectedProduct.getId())
                        .type(ProductType.builder()
                                .name("FOOD")
                                .build())
                        .subtype("Nuts")
                        .brand("Factor")
                        .price(2.5)
                        .quantity(1)
                        .build()));
        Product actualResult = productServiceImpl.update(expectedProduct,expectedProduct.getId());
        verify(productRepository,times(1)).findById(expectedProduct.getId());
        assertEquals(expectedProduct.getBrand(),actualResult.getBrand());
    }

}
