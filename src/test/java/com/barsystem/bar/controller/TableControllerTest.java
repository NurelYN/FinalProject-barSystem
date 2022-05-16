package com.barsystem.bar.controller;

import com.barsystem.bar.converter.TableConverter;
import com.barsystem.bar.dto.table.TableRequest;
import com.barsystem.bar.dto.table.TableResponse;
import com.barsystem.bar.model.Table;
import com.barsystem.bar.service.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value=TableController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TableControllerTest extends BaseControllerTest{

    @MockBean
    private TableService tableService;

    @MockBean
    private TableConverter tableConverter;

    @Test
    public void save() throws Exception{
        TableRequest tableRequest = TableRequest.builder()
                .number(1)
                .build();

        String reqJson = objectMapper.writeValueAsString(tableRequest);

        when(tableConverter.convert(any(TableRequest.class)))
                .thenReturn(Table.builder().build());
        when(tableService.save(any(Table.class)))
                .thenReturn(Table.builder().build());
        when(tableConverter.convert(any(Table.class)))
                .thenReturn(TableResponse.builder()
                        .id(1L)
                        .number(1)
                        .build());

        mockMvc.perform(post("/tables")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.number",is(1)));
    }
    @Test
    public void verifyFindAll() throws Exception {
        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void verifyDelete() throws Exception {
        mockMvc.perform(delete("/tables/1"))
                .andExpect(status().isOk());
    }
}

