package com.espark.adarsh.service;

import com.espark.adarsh.entity.ApplicationResponseBean;
import com.espark.adarsh.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    public List<Employee> getAllEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ApplicationResponseBean<List<Employee>>> responseEntity
                = restTemplate.exchange("http://localhost:8080/employees"
                , HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<List<Employee>>>() {
                }, new Object());
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

    public Employee getEmployee(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", id.toString());
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity =
                restTemplate.exchange("http://localhost:8080/employee/{id}"
                        , HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {
                        }, vars);
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

    public Employee removeEmployee(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", id.toString());
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange("http://localhost:8080/employee/{id}"
                , HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {
                }, vars);
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

    public Employee saveEmployee(Employee employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange("http://localhost:8080/employee"
                , HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {
                }, new Object());
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> httpEntity = new HttpEntity<>(employee, headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", id.toString());
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange("http://localhost:8080/employee/{id}"
                , HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {}, vars);
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

    public Employee updatePartialEmployee(@PathVariable("id") Long id, Map<String, Object> employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(employee, headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", id.toString());
        ResponseEntity<ApplicationResponseBean<Employee>> responseEntity
                = restTemplate.exchange("http://localhost:8080/employee/{id}"
                , HttpMethod.PATCH, httpEntity, new ParameterizedTypeReference<ApplicationResponseBean<Employee>>() {}, vars);
        return (responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody().getData() : null);
    }

}
