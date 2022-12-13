package com.espark.adarsh.controller;

import com.espark.adarsh.entities.Employee;
import com.espark.adarsh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // Get a all Employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    // Create a new Employee
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }


    // Get a Single Employee
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") String empId) {
        return this.employeeService.getEmployeeById(empId);
    }

    // Update a Employee
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") String empId,
                                   @RequestBody Employee employeeDetails) {
        return this.employeeService.updateEmployee(empId, employeeDetails);
    }

    @PatchMapping("/employee/{id}")
    public Employee updatePartialEmployee(@PathVariable("id") String id, @RequestBody Map<String, Object> employee) {
        return this.employeeService.updatePartialEmployee(id, employee);
    }

    // Delete a Employee
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") String empId) {
        Employee employee = this.employeeService.deleteEmployee(empId);
        return ResponseEntity.ok().body(employee);
    }

}