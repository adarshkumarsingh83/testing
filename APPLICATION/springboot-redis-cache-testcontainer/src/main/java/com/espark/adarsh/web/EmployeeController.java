package com.espark.adarsh.web;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return this.employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return this.employeeService.getEmployee(id);
    }

    @DeleteMapping("/employee/{id}")
    public Employee removeEmployee(@PathVariable("id") Long id) {
        return this.employeeService.removeEmployee(id);
    }

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return this.employeeService.saveEmployee(employee);
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        return this.employeeService.updateEmployee(id, employee);
    }

}
