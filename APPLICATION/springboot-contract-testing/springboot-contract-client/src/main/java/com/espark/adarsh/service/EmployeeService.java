package com.espark.adarsh.service;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    public List<Employee> getAllEmployee() {
        ResponseEntity<List<Employee>> responseEntity =  this.restTemplate.getForObject("http://localhost:9090/employees", ResponseEntity.class,new Object[]{});
        return responseEntity.getBody();
    }

    public Employee getEmployee(Long id) {
        ResponseEntity<Employee> responseEntity =  this.restTemplate.getForObject("http://localhost:9090/employee/{id}", ResponseEntity.class,id);
        return responseEntity.getBody();
    }

    public Employee removeEmployee(Long id) {
      return null;
    }

    public Employee saveEmployee(Employee employee) {
        return null;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        return null;
    }

    public Employee updatePartialEmployee(@PathVariable("id") Long id, Map<String, Object> employee) {
        return null;
    }

}
