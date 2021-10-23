package com.espark.adarsh;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.espark.adarsh.model.Employee;
import com.espark.adarsh.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringMockitoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper objectMapper = new ObjectMapper();
    Employee employee = null;

    @PostConstruct
    void init() throws Exception {
        File file = ResourceUtils.getFile("classpath:data.json");
        InputStream in = new FileInputStream(file);
        employee = objectMapper.readValue(in, Employee.class);
    }

    @Test
    public void addEmployeeTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(employee);
        MvcResult result = mockMvc.perform(post("/api/employee").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(resultContent, Response.class);
        Assert.isTrue(response.isStatus() == Boolean.TRUE);

    }

    @Test
    public void getEmployeesTest() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/employees").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(resultContent, Response.class);
        Assert.isTrue(response.isStatus() == Boolean.TRUE);
    }

}
