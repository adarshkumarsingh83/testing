package com.espark.adarsh.controller;

import java.util.List;

import com.espark.adarsh.model.Response;
import com.espark.adarsh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espark.adarsh.dao.EmployeeRepository;
import com.espark.adarsh.model.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee")
    public Response saveEmployee(@RequestBody Employee employee) {
        return this.employeeService.saveEmployee(employee);
    }

    @GetMapping("/employees")
    public Response getEmployees() {
        return this.employeeService.getEmployees();
    }
}
