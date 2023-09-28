package com.espark.adarsh;


import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationMainTests {

    @LocalServerPort
    private Integer port;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().usingFilesUnderClasspath("wiremock"))
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("api.server.url", wireMockServer::baseUrl);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getEmployee() {

        given().contentType(ContentType.JSON)
                .when()
                .get("/employees")
                .then()
                .body("message", startsWith("Successful"))
                .body("data", hasSize(4));
    }

    @Test
    void getEmployeeById() {
        Long id = 1L;
        given().contentType(ContentType.JSON)
                .when()
                .get("/employee/{id}", id)
                .then()
                .statusCode(200)
                .body("message", startsWith("Successful"))
                .body("data", notNullValue())
                .body("data.id", equalTo(1))
                .body("data.firstName", startsWith("adarsh"));
    }

}

