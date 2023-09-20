package com.espark.adarsh.web;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootRestTemplateWebTests {

    @LocalServerPort
    private int port;

    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", port);
    }


    @Autowired
    ResourceLoader resourceLoader;


    @Autowired
    ObjectMapper objectMapper;

    static String employeesResponse = null;
    static String employeeResponse = null;

    static String employeeSaveRequest = null;

    static String employeeUpdateRequest = null;

    @PostConstruct
    public void init() throws IOException {
        employeesResponse = resourceLoader.getResource("classpath:/store/employeesResponse.json")
                .getContentAsString(Charset.defaultCharset());
        employeeResponse = resourceLoader.getResource("classpath:/store/employeeResponse.json")
                .getContentAsString(Charset.defaultCharset());
        employeeSaveRequest = resourceLoader.getResource("classpath:/store/input/employeeSaveRequest.json")
                .getContentAsString(Charset.defaultCharset());
        employeeUpdateRequest = resourceLoader.getResource("classpath:/store/input/employeeUpdateRequest.json")
                .getContentAsString(Charset.defaultCharset());
    }

    @Test
    void contextLoads() {
    }


    @Test
    public void testGetAllEmployees() throws Exception {
        HttpEntity entity = new HttpEntity<>("");
        ResponseEntity<ResponseBean<List<Employee>>> response = this.restTemplate.exchange(url + "employees", HttpMethod.GET
                , entity, new ParameterizedTypeReference<ResponseBean<List<Employee>>>() {
                });
        ResponseBean<List<Employee>> responseBean = response.getBody();
        System.out.println(responseBean.getData());
    }

    @Test
    public void testGetEmployees() throws Exception {
        HttpEntity entity = new HttpEntity<>("");
        ResponseEntity<ResponseBean<Employee>> response = this.restTemplate.exchange(url + "employee/1", HttpMethod.GET
                , entity, new ParameterizedTypeReference<ResponseBean<Employee>>() {
                });
        ResponseBean<Employee> responseBean = response.getBody();
        System.out.println(responseBean.getData());
    }


    @Test
    public void testGetFilterEmployees() throws Exception {
        EmployeeFilter filter = new EmployeeFilter();
        filter.setSalary(new FilterField() {
            {
                setOperator("gt");
                setValue("5");
            }
        });
        Map<String, String> headers = new HashMap<>() {
            {
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
            }
        };
        HttpEntity entity = new HttpEntity<>(filter);
        ResponseEntity<ResponseBean<Iterable<Employee>>> response = this.restTemplate.exchange(url + "employee/filter", HttpMethod.POST
                , entity, new ParameterizedTypeReference<ResponseBean<Iterable<Employee>>>() {
                });
        ResponseBean<Iterable<Employee>> responseBean = response.getBody();
        System.out.println(responseBean.getData());
    }



	@Test
	public void testSaveEmployees() throws Exception {

        Map<String, String> headers = new HashMap<>() {
            {
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
            }
        };
        HttpEntity entity = new HttpEntity<>(objectMapper.readValue(employeeSaveRequest,Employee.class));
        ResponseEntity<ResponseBean<Employee>> response = this.restTemplate.exchange(url + "employee", HttpMethod.POST
                , entity, new ParameterizedTypeReference<ResponseBean<Employee>>() {
                });
        ResponseBean<Employee> responseBean = response.getBody();
        System.out.println(responseBean.getData());
	}


	@Test
	public void testUpdateEmployees() throws Exception {

        Map<String, String> headers = new HashMap<>() {
            {
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
            }
        };
        HttpEntity entity = new HttpEntity<>(objectMapper.readValue(employeeUpdateRequest,Employee.class));
        ResponseEntity<ResponseBean<Employee>> response = this.restTemplate.exchange(url + "employee", HttpMethod.PUT
                , entity, new ParameterizedTypeReference<ResponseBean<Employee>>() {
                });
        ResponseBean<Employee> responseBean = response.getBody();
        System.out.println(responseBean.getData());
	}


}
