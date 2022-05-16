package com.barsystem.bar.service;


import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.User;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.repository.UserTypeRepository;
import com.barsystem.bar.service.impl.UserTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    private UserTypeServiceImpl userTypeServiceImpl;

    @BeforeEach
    public void setUp(){
        userTypeServiceImpl = new UserTypeServiceImpl(userTypeRepository);
    }

    @Test
    public void verifySave(){
        UserType userType = UserType.builder().build();
        when(userTypeRepository.save(any(UserType.class))).thenReturn(userType);
        userTypeServiceImpl.save(userType);
        verify(userTypeRepository,times(1)).save(userType);
    }

    @Test
    public void verifySaveNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            userTypeServiceImpl.save(null);
        });
    }

    @Test
    public void verifySaveThrowsDuplicateRecordException(){
        UserType userType = UserType.builder()
                .name("MANAGER")
                .build();

        String expectedMessage = "User type with this name: MANAGER already exist";
        when(userTypeServiceImpl.save(userType))
                .thenThrow(DataIntegrityViolationException.class);

        DuplicateRecordException duplicateRecordException = assertThrows(DuplicateRecordException.class,
                () -> userTypeServiceImpl.save(userType));

        assertEquals(expectedMessage,duplicateRecordException.getMessage());
    }

    @Test
    public void verifyFindAll(){
        userTypeServiceImpl.findAll();
        verify(userTypeRepository,times(1)).findAll();
    }

    @Test
    public void verifyFindById(){
        Long id = 5L;
        when(userTypeRepository.findById(id))
                .thenReturn(Optional.of(UserType.builder()
                        .id(5L)
                        .build()));
        UserType actualUserType = userTypeServiceImpl.findById(id);
        assertEquals(id,actualUserType.getId());
        verify(userTypeRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage="User type with id: 1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            userTypeServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindByName(){
        String name = "MANAGER";
        when(userTypeRepository.findByName(name))
                .thenReturn(Optional.of(UserType.builder()
                        .name("MANAGER")
                        .build()));
        UserType actualUserType = userTypeServiceImpl.findByName(name);
        assertEquals(name,actualUserType.getName());
        verify(userTypeRepository,times(1)).findByName(name);
    }

    @Test
    public void verifyFindByNameRecordNotFoundException(){
        String expectedMessage="User type with name: MANAGER is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            userTypeServiceImpl.findByName("MANAGER");
        });
        assertEquals(expectedMessage,ex.getMessage());
    }


    @Test
    void verifyDeleteById() {
        Long id = 1L;
        userTypeServiceImpl.delete(id);

        verify(userTypeRepository,times(1)).deleteById(id);
    }

    @Test
    void verifyDeleteByName() {
        String name = "MANAGER";
        userTypeServiceImpl.delete(name);

        verify(userTypeRepository,times(1)).deleteByName(name);
    }

    @Test
    public void verifyUpdateSucces(){
        UserType expectedUserType = UserType.builder()
                .id(2L)
                .name("MANAGER")
                .build();
        when(userTypeRepository.findById(expectedUserType.getId()))
                .thenReturn(Optional.of(UserType.builder()
                        .id(expectedUserType.getId())
                        .name("WAITER")
                        .build()));
        // !
        UserType actualResult = userTypeServiceImpl.update(expectedUserType,expectedUserType.getId());

        verify(userTypeRepository,times(1)).findById(expectedUserType.getId());
        assertEquals(expectedUserType.getName(),actualResult.getName());
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenIdIsNull(){
        assertThrows(NullPointerException.class,()->{
            userTypeServiceImpl.update(UserType.builder().build(),null);
        });
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenUpdateUserIsNull(){
        assertThrows(NullPointerException.class,()->{
            userTypeServiceImpl.update(null,5L);
        });
    }


}
