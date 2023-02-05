package com.espark.adarsh;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import com.espark.adarsh.web.EmployeeController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(classes = SpringbootContractServerApplication.class)
public abstract class SpringbootContractServerApplicationTests {

    private static final Employee employeeInput = new Employee("adarsh", "kumar", "It");
    private static final Employee employeeResponse = new Employee(1L, "adarsh", "kumar", "It");
    private static final Map<String,Object> employeeMap = new HashMap<>() {
        {
            put("id","1");
            put("firstName","adarsh");
            put("lastName", "kumar");
        }
    };
    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    EmployeeController employeeController;

    @BeforeEach
    public void init() {
        RestAssuredMockMvc.standaloneSetup(employeeController);

        Mockito.when(employeeService.saveEmployee(any(Employee.class)))
                .thenReturn(employeeResponse);

        Mockito.when(employeeService.getEmployee(anyLong()))
                .thenReturn(employeeResponse);

        Mockito.when(employeeService.getAllEmployee())
                .thenReturn(Arrays.asList(employeeResponse));

        Mockito.when(employeeService.removeEmployee(anyLong()))
                .thenReturn(employeeResponse);

        Mockito.when(employeeService.updateEmployee(anyLong(), any(Employee.class)))
                .thenReturn(employeeResponse);

        Mockito.when(employeeService.updatePartialEmployee(anyLong(), any(Map.class)))
                .thenReturn(employeeResponse);
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(webApplicationContext);
    }

    @Test
    void saveEmployeeTest() {
        Assertions.assertNotNull(employeeService.saveEmployee(employeeInput));
    }

    @Test
    void getEmployeeTest() {
        Assertions.assertNotNull(employeeService.getEmployee(1L));
    }


    @Test
    void getAllEmployeeTest() {
        Assertions.assertNotNull(employeeService.getAllEmployee());
    }

    @Test
    void updatePartialEmployeeTest() {
        Assertions.assertNotNull(employeeService.updatePartialEmployee(1L, employeeMap));
    }

    @Test
    void updateEmployeeTest() {
        Assertions.assertNotNull(employeeService.updateEmployee(1L, employeeInput));
    }

    @Test
    void removeEmployee() {
        Assertions.assertNotNull(employeeService.removeEmployee(1L));
    }




}
