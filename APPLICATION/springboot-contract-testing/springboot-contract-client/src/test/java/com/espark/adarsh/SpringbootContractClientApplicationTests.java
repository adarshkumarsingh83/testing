package com.espark.adarsh;

import com.espark.adarsh.entity.ApplicationResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = SpringbootContractClientApplication.class)
@AutoConfigureStubRunner(
        ids = "com.espark.adarsh:springboot-contract-server:0.0.1-SNAPSHOT:stubs:8100",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class SpringbootContractClientApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllEmployee() {
        List<Employee> employees =  this.employeeService.getAllEmployee();
        Assertions.assertNotNull(employees);
        Assertions.assertTrue(employees.size() != 0);
    }

    @Test
    public void getEmployee() {
        Employee employee = employeeService.getEmployee(1L);
        Assertions.assertNotNull(employee);
    }

    @Test
    public void removeEmployee(){
        Employee employee = employeeService.removeEmployee(1L);
        Assertions.assertNotNull(employee);
    }


    @Test
    public void saveEmployee(){
        Employee employeeInput = new Employee(1l,"adarsh", "kumar", "It");
        Employee employee = this.employeeService.saveEmployee(employeeInput);
        Assertions.assertNotNull(employee);
    }

    @Test
    public void updateEmployee(){
        Employee employeeInput = new Employee(1l,"adarsh", "kumar", "It-team");
        Employee employee = this.employeeService.updateEmployee(1l,employeeInput);
        Assertions.assertNotNull(employee);
    }

    @Test
    public void updatePartialEmployee(){
        Map<String,Object> employeeDate = new HashMap<>(){
            {
                put("id","1");
                put("firstName","adarsh");
                put("career","It-Head");
            }
        };
        Employee employee = this.employeeService.updatePartialEmployee(1l,employeeDate);
        Assertions.assertNotNull(employee);
    }

}
