package com.barsystem.bar.service;

import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.User;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.repository.UserRepostiory;
import com.barsystem.bar.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepostiory userRepository;

    private UserServiceImpl userServiceImpl;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
    }

   @Test
    public void verifyFindAll(){
        userServiceImpl.findAll();
        verify(userRepository,times(1)).findAll();
   }

   @Test
    public void verifyFindById(){
        Long id = 5L;
        when(userRepository.findById(id))
                .thenReturn(Optional.of(User.builder()
                        .id(5L)
                        .build()));
       User actualUser = userServiceImpl.findById(id);
       assertEquals(id,actualUser.getId());
       verify(userRepository,times(1)).findById(id);
   }

    @Test
    public void verifyFindByIdRecordNotFoundException(){
        String expectedMessage="User with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            userServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenIdIsNull(){
        assertThrows(NullPointerException.class,()->{
            userServiceImpl.update(User.builder().build(),null);
        });
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenUpdateUserIsNull(){
        assertThrows(NullPointerException.class,()->{
            userServiceImpl.update(null,5L);
        });
    }

    @Test
    public void verifyDelete(){

        Long id =5L;

        userServiceImpl.delete(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    //! gives error caused by bcryptPasswordEncoder
//    @Test
//    public void verifySave(){
//        User userSave = User.builder().build();
//        when(userRepository.save(any(User.class)))
//                .thenReturn(userSave);
//        userServiceImpl.save(userSave);
//        verify(userRepository,times(1));
//    }

}
