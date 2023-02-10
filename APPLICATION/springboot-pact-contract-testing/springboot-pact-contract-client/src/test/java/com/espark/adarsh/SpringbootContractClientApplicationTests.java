package com.espark.adarsh;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;

import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.espark.adarsh.entity.ApplicationResponseBean;
import com.espark.adarsh.entity.Employee;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME,
        hostInterface = "localhost", port = "9080")
class SpringbootContractClientApplicationTests {

    public static final String PACT_PROVIDER_NAME ="service_provider";
    public static final String PACT_CONSUMER_NAME ="service_consumer";

    private RestTemplate restTemplate;



    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
    ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact getEmployeeById(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        val body = new PactDslJsonBody();
        body.object("data", new PactDslJsonBody()
                        .numberValue("id", 1)
                        .stringValue("firstName", "adarsh")
                        .stringValue("lastName", "kumar")
                        .stringValue("career", "It")
                ).stringValue("message", "Data Fetch Successfully")
                .closeObject();
        return builder
                .given("Test GET employee with id ")
                .uponReceiving("GET REQUEST")
                .path("/employee/1")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toPact();
    }


    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
            ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact removeEmployeeById(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        val body = new PactDslJsonBody();
        body.object("data", new PactDslJsonBody()
                        .numberValue("id", 1)
                        .stringValue("firstName", "adarsh")
                        .stringValue("lastName", "kumar")
                        .stringValue("career", "It")
                ).stringValue("message", "Data Deleted Successfully")
                .closeObject();
        return builder
                .given("Test DELETE employee BY id ")
                .uponReceiving("DELETE REQUEST")
                .path("/employee/1")
                .method(HttpMethod.DELETE.name())
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toPact();
    }


    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
            ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact getEmployees(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        val body = new PactDslJsonBody();
        body.stringValue("message", "Data Fetch Successfully")
                .eachLike("data")
                .numberValue("id", 1)
                .stringValue("firstName", "adarsh")
                .stringValue("lastName", "kumar")
                .stringValue("career", "It")
                .closeObject()
                .object()
                .numberValue("id", 2)
                .stringValue("firstName", "radha")
                .stringValue("lastName", "singh")
                .stringValue("career", "It")
                .closeObject()
                .object()
                .numberValue("id", 3)
                .stringValue("firstName", "sonu")
                .stringValue("lastName", "singh")
                .stringValue("career", "It")
                .closeObject()
                .object()
                .numberValue("id", 4)
                .stringValue("firstName", "amit")
                .stringValue("lastName", "kumar")
                .stringValue("career", "Finance")
                .closeObject()
                .closeArray();
        return builder
                .given("Test Get Employees")
                .uponReceiving("GET REQUEST")
                .path("/employees")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toPact();
    }


    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
            ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact saveEmployee(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        val requestBody = new PactDslJsonBody();
        requestBody
                .stringValue("firstName", "adarsh")
                .stringValue("lastName", "kumar")
                .stringValue("career", "It")
                .closeObject();

        val responseBody = new PactDslJsonBody();
        responseBody.object("data", new PactDslJsonBody()
                        .numberValue("id", 1)
                        .stringValue("firstName", "adarsh")
                        .stringValue("lastName", "kumar")
                        .stringValue("career", "It")
                ).stringValue("message", "Data Saved Successfully")
                .closeObject();

        return builder
                .given("Test Post Employee ")
                .uponReceiving("POST REQUEST")
                .path("/employee")
                .method(HttpMethod.POST.name())
                .body(requestBody)
                .headers(headers)
                .willRespondWith()
                .status(201)
                .headers(headers)
                .body(responseBody)
                .toPact();
    }




    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
            ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact updateEmployee(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        val requestBody = new PactDslJsonBody();
        requestBody
                .numberValue("id", 1)
                .stringValue("firstName", "adarsh")
                .stringValue("lastName", "kumar-singh")
                .stringValue("career", "It")
                .closeObject();

        val responseBody = new PactDslJsonBody();
        responseBody.object("data", new PactDslJsonBody()
                        .numberValue("id", 1)
                        .stringValue("firstName", "adarsh")
                        .stringValue("lastName", "kumar-singh")
                        .stringValue("career", "It")
                ).stringValue("message", "Data Updated Successfully")
                .closeObject();

        return builder
                .given("Test Put Employee ")
                .uponReceiving("PUT REQUEST")
                .path("/employee/1")
                .method(HttpMethod.PUT.name())
                .body(requestBody)
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(responseBody)
                .toPact();
    }

    @Pact(consumer = SpringbootContractClientApplicationTests.PACT_CONSUMER_NAME
            ,provider = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public RequestResponsePact partialUpdateEmployee(PactDslWithProvider builder) {
        val headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        val requestBody = new PactDslJsonBody();
        requestBody
                .numberValue("id", 1)
                .stringValue("firstName", "adarsh")
                .stringValue("career", "It-Head")
                .closeObject();

        val responseBody = new PactDslJsonBody();
        responseBody.object("data", new PactDslJsonBody()
                        .numberValue("id", 1)
                        .stringValue("firstName", "adarsh")
                        .stringValue("lastName", "kumar")
                        .stringValue("career", "It-Head")
                ).stringValue("message", "Data Updated Successfully")
                .closeObject();

        return builder
                .given("Test Patch Employee ")
                .uponReceiving("PATCH REQUEST")
                .path("/employee/1")
                .method(HttpMethod.PATCH.name())
                .body(requestBody)
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(responseBody)
                .toPact();
    }

    @BeforeEach
    public void setUp(MockServer mockServer) {
        System.out.println("Mockserver check called");
        Assertions.assertTrue(mockServer != null);
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }



    @Test
    @PactTestFor(pactMethod = "getEmployeeById"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifyGetEmployeeByIdPact(MockServer mockServer) {
        val result = restTemplate
                .getForObject(mockServer.getUrl() + "/employee/1", ApplicationResponseBean.class);
        Assertions.assertEquals(result.toString(), "ApplicationResponseBean(data={firstName=adarsh, lastName=kumar, career=It, id=1}, message=Data Fetch Successfully)");
    }

    @Test
    @PactTestFor(pactMethod = "getEmployees"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifyGetEmployeesPact(MockServer mockServer) {
        val result =restTemplate
                .getForObject(mockServer.getUrl() + "/employees", ApplicationResponseBean.class);
        Assertions.assertEquals(result.toString(), "ApplicationResponseBean(data=[{firstName=adarsh, lastName=kumar, career=It, id=1}, {firstName=radha, lastName=singh, career=It, id=2}, {firstName=sonu, lastName=singh, career=It, id=3}, {firstName=amit, lastName=kumar, career=Finance, id=4}], message=Data Fetch Successfully)");
    }

    @Test
    @PactTestFor(pactMethod = "saveEmployee"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifySaveEmployeePact(MockServer mockServer) {
        Employee employee = new Employee();
        employee.setId(null);
        employee.setFirstName("adarsh");
        employee.setLastName("kumar");
        employee.setCareer("It");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(employee,headers);
        val result = restTemplate
                .postForObject(mockServer.getUrl() + "/employee", httpEntity,ApplicationResponseBean.class);
        Assertions.assertEquals(result.toString(), "ApplicationResponseBean(data={firstName=adarsh, lastName=kumar, career=It, id=1}, message=Data Saved Successfully)");
    }


    @Test
    @PactTestFor(pactMethod = "removeEmployeeById"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifyDeleteEmployeeByIdPact(MockServer mockServer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", "1");

        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange(mockServer.getUrl() +"/employee/{id}"
                , HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {
                }, vars);

        Assertions.assertEquals(responseEntity.getBody().getData().toString(), "Employee{id=1, firstName='adarsh', lastName='kumar', career='It'}");
    }


    @Test
    @PactTestFor(pactMethod = "updateEmployee"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifyUpdateEmployeePact(MockServer mockServer) {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("adarsh");
        employee.setLastName("kumar-singh");
        employee.setCareer("It");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(employee, headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", "1");
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange(mockServer.getUrl()+"/employee/{id}"
                , HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {}, vars);
        Assertions.assertEquals(responseEntity.getBody().getData().toString(), "Employee{id=1, firstName='adarsh', lastName='kumar-singh', career='It'}");
    }


    //
    @Test
    @PactTestFor(pactMethod = "partialUpdateEmployee"
            ,providerName = SpringbootContractClientApplicationTests.PACT_PROVIDER_NAME)
    public void verifyPartialUpdateEmployeePact(MockServer mockServer) {
        Map<String,Object> employee = new HashMap<>();
        employee.put("id",1);
        employee.put("firstName","adarsh");
        employee.put("career", "It-Head");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(employee, headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", "1");
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange(mockServer.getUrl()+"/employee/{id}"
                , HttpMethod.PATCH, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {}, vars);
        Assertions.assertEquals(responseEntity.getBody().getData().toString(), "Employee{id=1, firstName='adarsh', lastName='kumar', career='It-Head'}");
    }


}
