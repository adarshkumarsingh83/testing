package com.espark.adarsh;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationMain.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class ApplicationMainTests {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllEmployee() throws Exception {
        Employee employeeAdmin = new Employee(10l, "admin", "admin");
        Employee employeeUser = new Employee(20l, "user", "user");
        employeeService.createEmployee(employeeAdmin);
        employeeService.createEmployee(employeeUser);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void testGetEmployee() throws Exception {
        Employee employeeAdmin = new Employee(1l, "admin", "admin");
        Employee employeeUser = new Employee(2l, "user", "user");
        employeeService.createEmployee(employeeAdmin);
        employeeService.createEmployee(employeeUser);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/employee/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void testSaveEmployee() throws Exception {
        Employee employeeAdmin = new Employee(1l, "admin", "admin");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/employee")
                        .content(new ObjectMapper().writeValueAsString((employeeAdmin)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employeeAdmin = new Employee(1l, "admin", "admin@admin");
        employeeService.createEmployee(employeeAdmin);
        Employee employeeAdminToUpdate = new Employee(1l, "admin", "admin");
        employeeService.createEmployee(employeeAdmin);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/employee/{id}", 1)
                        .content(new ObjectMapper().writeValueAsString((employeeAdminToUpdate)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("admin"));
    }


    @Test
    public void deleteEmployeeAPI() throws Exception {
        Employee employeeAdmin = new Employee(1l, "admin", "admin@admin");
        employeeService.createEmployee(employeeAdmin);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

}
