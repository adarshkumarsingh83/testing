package com.espark.adarsh;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationMainTests {

    @LocalServerPort
    private Integer port;

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("api.server.url", wireMock::baseUrl);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getEmployee() {

        wireMock.stubFor(
                WireMock.get(urlMatching("/employees"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                                        .withBody(
                                                """
                                              {
                                                  "data": [
                                                    {
                                                      "id": 1,
                                                      "firstName": "adarsh",
                                                      "lastName": "kumar",
                                                      "career": "It",
                                                      "salary": 10,
                                                      "doj": "2020-01-01",
                                                      "gender": "MALE",
                                                      "attributes": {
                                                        "key1": "value"
                                                      }
                                                    },
                                                    {
                                                      "id": 2,
                                                      "firstName": "radha",
                                                      "lastName": "singh",
                                                      "career": "IT",
                                                      "salary": 10,
                                                      "doj": "2020-01-01",
                                                      "gender": "FEMALE",
                                                      "attributes": {
                                                        "key2": "value"
                                                      }
                                                    },
                                                    {
                                                      "id": 3,
                                                      "firstName": "sonu",
                                                      "lastName": "singh",
                                                      "career": "IT",
                                                      "salary": 5,
                                                      "doj": "2020-01-01",
                                                      "gender": "MALE",
                                                      "attributes": {
                                                        "key3": "value"
                                                      }
                                                    },
                                                    {
                                                      "id": 4,
                                                      "firstName": "amit",
                                                      "lastName": "kumar",
                                                      "career": "Finance",
                                                      "salary": 8,
                                                      "doj": "2020-01-01",
                                                      "gender": "MALE",
                                                      "attributes": {
                                                        "key4": "value"
                                                      }
                                                    }
                                                  ],
                                                  "message": "Successful "
                                            }
                                        """)));

        given().contentType(ContentType.JSON)
                .when()
                .get("/employees")
                .then()
                .body("message", startsWith("Successful"))
                .body("data", hasSize(4));
    }

    @Test
    void getEmployeeById() {

        wireMock.stubFor(
                WireMock.get(urlMatching("/employee/1"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                                        .withBody(
                                                """
                                                {
                                                  "data": {
                                                    "id": 1,
                                                    "firstName": "adarsh",
                                                    "lastName": "kumar",
                                                    "career": "It",
                                                    "salary": 10,
                                                    "doj": "2020-01-01",
                                                    "gender": "MALE",
                                                    "attributes": {
                                                      "key1": "value"
                                                    }
                                                  },
                                                  "message": "Successful"
                                                }
                                        """)));

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

