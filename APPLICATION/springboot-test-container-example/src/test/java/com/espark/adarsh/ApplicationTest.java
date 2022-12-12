package com.espark.adarsh;

import com.espark.adarsh.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Paths;
import java.util.List;

@Slf4j
@SpringBootTest
@Testcontainers
public class ApplicationTest {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Container
    public static GenericContainer container = new GenericContainer(
            new ImageFromDockerfile("adarshkumarsingh83/springboot-test-container", false)
                    .withFileFromPath(".", Paths.get("./"))
                    .withBuildArg("SOME_ARGS", "ESPARK-ARGS"))
            .withEnv("DELAY_START_MSEC", "10")
            .withExposedPorts(8080, 8080)
             .waitingFor(Wait.forLogMessage(".* 8080 .*\\n", 1));


    @Test
    @DisplayName("test api")
    public void testGetApi(){
        log.info(container.getLogs());
        String baseUrl = "http://" + container.getHost() + ":" + container.getFirstMappedPort();
        RestTemplate restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
        ResponseEntity<List<Employee>> response = restTemplate.exchange("/employees", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }, new Object[]{});
        log.info("Response from Test Container  {}", response);
    }
}
