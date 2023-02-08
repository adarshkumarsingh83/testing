package com.espark.adarsh;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;

import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.espark.adarsh.entity.ApplicationResponseBean;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "service_provider", hostInterface="localhost",port = "9080")
class SpringbootContractClientApplicationTests {

    @Pact(consumer = "service_consumer")
    public RequestResponsePact getEmployeeById(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        val body = new PactDslJsonBody();
        body.object("data",new PactDslJsonBody()
                        .numberValue("id",1)
                        .stringValue("firstName","adarsh")
                        .stringValue("lastName","kumar")
                        .stringValue("career","It")
                ).stringValue("message", "Data Fetch Successfully")
                .closeObject();
        return builder
                .given("Test GET employee with id ")
                .uponReceiving("GET REQUEST")
                .path("/employee/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toPact();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @PactTestFor(pactMethod = "getEmployeeById")
    public void verifyGetEmployeeByIdPact(MockServer mockServer) {
        val result = new RestTemplate()
                .getForObject(mockServer.getUrl()+"/employee/1", ApplicationResponseBean.class);
        Assertions.assertEquals(result.toString(), "ApplicationResponseBean(data={firstName=adarsh, lastName=kumar, career=It, id=1}, message=Data Fetch Successfully)");
    }


   /* @Test
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
    }*/

}
