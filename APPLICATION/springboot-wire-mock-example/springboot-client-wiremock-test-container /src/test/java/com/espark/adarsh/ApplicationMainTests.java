package com.espark.adarsh;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class ApplicationMainTests {

    @LocalServerPort
    private Integer port;
    // https://testcontainers.com/guides/testing-rest-api-integrations-using-wiremock/
    // https://github.com/testcontainers/tc-guide-testing-rest-api-integrations-using-wiremock/blob/main/src/test/java/com/testcontainers/demo/AlbumControllerTestcontainersTests.java

    @Container
    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:2.35.0")
            . withMappingFromJSON("get-employees", "get-employees.json")
            .withFileFromResource("get-employees-resp-200.json", "wiremock/get-employees-resp-200.json")
            .withMapping("employee-by-id", "get-employes-by-id.json")
            .withFileFromResource("get-employee-by-id-resp-200", "wiremock/get-employee-by-id-resp-200.json");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("api.server.url", wiremockServer::getBaseUrl);
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

