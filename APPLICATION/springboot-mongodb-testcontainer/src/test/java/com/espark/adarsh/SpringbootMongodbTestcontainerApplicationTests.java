package com.espark.adarsh;

import com.espark.adarsh.entities.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootMongodbTestcontainerApplicationTests {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int localServerPort;

    @Container
    static MongoDBContainer container = new  MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
            .withExposedPorts(27017);

    @BeforeAll
    static void setUpAll() {
        container.waitingFor(Wait.forListeningPort()
                       .withStartupTimeout(Duration.ofSeconds(180L)));
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @Test
    public void testGetAllEmployees() {
        container.start();
        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + localServerPort + "/api").build();
        ResponseEntity<List<Employee>> response = restTemplate.exchange("/employee", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }, new Object[]{});
        log.info("Response from Test Container  {}", response);
    }


    @Test
    public void testGetByIdEmployees() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + localServerPort + "/api").build();
        ResponseEntity<Employee> response = restTemplate.getForEntity("/employee/{id}", Employee.class, params);
        log.info("Response from Test Container  {}", response);
    }

    @Test
    void testPostEmployeeApi() {
        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + localServerPort + "/api").build();
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("admin");
        newEmployee.setLastName("admin@espark.com");
        ResponseEntity<Employee> response = restTemplate.postForEntity("/employee", newEmployee, Employee.class);
        log.info("Response from Test Container  {}", response);
    }

    @Test
    void testPutEmployeeApi() {
        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + localServerPort + "/api").build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("ADARSH KUMAR");
        HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(employee, httpHeaders);
        ResponseEntity<Employee> response = restTemplate.exchange("/employee/{id}", HttpMethod.PUT, httpEntity, Employee.class, params);
        log.info("Response from Test Container  {}", response);
    }


    @Test
    void testPatchEmployeeApi() {

        RestTemplate restTemplate = restTemplateBuilder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .rootUri("http://localhost:" + localServerPort + "/api")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        Map<String, String> employee = new HashMap<String, String>() {
            {
                put("email", "adarsh.kumar@espark.com");
            }
        };
        HttpEntity<Map> httpEntity = new HttpEntity<Map>(employee, httpHeaders);
        ResponseEntity<Employee> response = restTemplate.exchange("/employee/{id}", HttpMethod.PATCH, httpEntity, Employee.class, params);
        log.info("Response from Test Container  {}", response);
    }

    @Test
    void testDeleteEmployeeApi() {
        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + localServerPort + "/api").build();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        ResponseEntity<Employee> response = restTemplate.exchange("/employee/{id}", HttpMethod.DELETE, null, Employee.class, params);
        log.info("Response from Test Container  {}", response);
    }

}

