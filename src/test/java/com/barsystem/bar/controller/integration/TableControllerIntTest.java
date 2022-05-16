package com.barsystem.bar.controller.integration;

import com.barsystem.bar.dto.table.TableRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TableControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void save() throws Exception {
//        TableRequest tableRequest = TableRequest.builder()
//                .number(1)
//                .build();
//        String reqJson = objectMapper.writeValueAsString(tableRequest);
//
//       given()
//               .contentType(ContentType.JSON.toString())
//               .body(reqJson)
//                    .when()
//                        .post("http://localhost:8090/tables")
//               .then()
//                    .statusCode(HttpStatus.CREATED.value())
//                    .body("id",equalTo(1))
//                    .body("number",equalTo(1));
//        System.out.println(5);
//    }
}
