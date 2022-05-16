package com.barsystem.bar.controller;

import com.barsystem.bar.converter.UserTypeConverter;
import com.barsystem.bar.dto.userType.UserTypeRequest;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.service.UserTypeService;
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

@WebMvcTest(value=UserTypeController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserTypeControllerTest extends BaseControllerTest{

    @MockBean
    private UserTypeService userTypeService;

    @MockBean
    private UserTypeConverter userTypeConverter;

    @Test
    public void save() throws Exception {
        UserTypeRequest userTypeRequest = UserTypeRequest.builder()
                .name("MANAGER")
                .build();

        String reqJson= objectMapper.writeValueAsString(userTypeRequest);

        when(userTypeConverter.convert(any(UserTypeRequest.class)))
                .thenReturn(UserType.builder().build());
        when(userTypeService.save(any(UserType.class)))
                .thenReturn(UserType.builder()
                        .id(1L)
                        .name("MANAGER")
                        .build());

        mockMvc.perform(post("/usertypes")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("MANAGER")));
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/usertypes"))
                .andExpect(status().isOk());

    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/usertypes/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByName() throws Exception {
        mockMvc.perform(get("/usertypes/name/anyName"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteByName() throws Exception {
        mockMvc.perform(delete("/usertypes/name/FOOD"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(delete("/usertypes/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateHappy() throws Exception {
        UserTypeRequest userTypeRequest = UserTypeRequest.builder()
                .name("WAITER")
                .build();
        String reqJson = objectMapper.writeValueAsString(userTypeRequest);

        when(userTypeConverter.convert(any(UserTypeRequest.class)))
                .thenReturn(UserType.builder().build());
        when(userTypeService.update(any(UserType.class),any(Long.class)))
                .thenReturn(UserType.builder()
                        .id(1L)
                        .name("UPDATED_WAITER")
                        .build());
        mockMvc.perform(put("/usertypes/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("UPDATED_WAITER")));
    }

}
