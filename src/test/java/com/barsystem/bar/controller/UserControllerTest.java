package com.barsystem.bar.controller;

import com.barsystem.bar.converter.UserConverter;
import com.barsystem.bar.dto.user.UserResponse;
import com.barsystem.bar.dto.user.UserSaveRequest;
import com.barsystem.bar.dto.user.UserUpdateRequest;
import com.barsystem.bar.dto.userType.UserTypeRequest;
import com.barsystem.bar.model.User;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value=UserController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserControllerTest extends BaseControllerTest{

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter userConverter;

    @Test
    public void save() throws Exception {
        UserSaveRequest userRequest = UserSaveRequest.builder()
                .name("Nurel")
                .pin("0000")
                .phone("0898792086")
                .userType("MANAGER")
                .build();

        String reqJson = objectMapper.writeValueAsString(userRequest);

        when(userConverter.convert(any(UserSaveRequest.class)))
                .thenReturn(User.builder().build());
        when(userService.save(any(User.class)))
                .thenReturn(User.builder().build());
        when(userConverter.convert(any(User.class)))
                .thenReturn(UserResponse.builder()
                        .id(1L)
                        .name("Nurel")
                        .phone("0898792086")
                        .userType(UserType.builder()
                                .id(1L)
                                .name("MANAGER")
                                .build())
                        .build());

        mockMvc.perform(post("/users")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Nurel")))
                .andExpect(jsonPath("$.phone",is("0898792086")));
    }

    @Test
    public void findAll () throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        when(userService.findById(any(Long.class)))
                .thenReturn(User.builder().build());
        when(userConverter.convert(any(User.class)))
                .thenReturn(UserResponse.builder()
                                .id(1L)
                                .name("Nurel")
                                .phone("0898792086")
                                .userType(UserType.builder()
                                        .id(1L)
                                        .name("MANAGER")
                                        .build())
                                .build());

        mockMvc.perform(get("/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Nurel")))
                .andExpect(jsonPath("$.phone",is("0898792086")));
    }

    @Test
    public void deleteHappy() throws Exception {
        mockMvc.perform(delete("/users/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        UserUpdateRequest userUpdateRequestRequest = UserUpdateRequest.builder()
                .name("Nurel")
                .pin("0000")
                .phone("0898792086")
                .userType("MANAGER")
                .build();

        String reqJson=objectMapper.writeValueAsString(userUpdateRequestRequest);

        when(userConverter.convert(any(UserUpdateRequest.class)))
                .thenReturn(User.builder().build());
        when(userService.update(any(User.class),any(Long.class)))
                .thenReturn(User.builder().build());
        when(userConverter.convert(any(User.class)))
                .thenReturn(UserResponse.builder()
                        .id(1L)
                        .name("Nurel")
                        .phone("0898792086")
                        .userType(UserType.builder()
                                .id(1L)
                                .name("MANAGER")
                                .build())
                        .build());

        mockMvc.perform(put("/users/id/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Nurel")))
                .andExpect(jsonPath("$.phone",is("0898792086")));
    }

}
