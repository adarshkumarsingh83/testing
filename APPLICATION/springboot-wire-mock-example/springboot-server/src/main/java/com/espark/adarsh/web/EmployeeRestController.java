package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRestController {

    @Autowired
    EmployeeService  employeeService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/employees")
    public ResponseBean<List<Employee>> getAllEmployee() {
       return ResponseBean.<List<Employee>>builder()
                .data(this.employeeService.getAllEmployee())
                .message("Successful ")
                .build();
    }

    @GetMapping("/employee/{id}")
    public ResponseBean<Employee> getEmployee(@PathVariable("id") Long id) {
        return ResponseBean.<Employee>builder()
                .data(this.employeeService.getEmployee(id))
                .message("Successful")
                .build();
    }

    @PostMapping("/employee")
    public ResponseBean<Employee> saveEmployee(@RequestBody @Valid EmployeeBean employeeBean) throws JsonProcessingException {
        return ResponseBean.<Employee>builder()
                .data(this.employeeService.saveEmployee(employeeBean.getEmployee()))
                .message("Successful")
                .build();
    }

   @DeleteMapping("/employee/{id}")
    public ResponseBean<Employee> removeEmployee(@PathVariable("id") Long id) {
       return ResponseBean.<Employee>builder()
               .data(this.employeeService.removeEmployee(id))
               .message("Successful")
               .build();
    }



    @PutMapping("/employee")
    public ResponseBean<Employee> updateEmployee(@RequestBody @Valid EmployeeBean employeeBean) {
        return ResponseBean.<Employee>builder()
                .data(this.employeeService.updateEmployee(employeeBean.getId(),employeeBean.getEmployee()))
                .message("Successful")
                .build();
    }


    @PostMapping("/employee/filter")
    public ResponseBean<Iterable<Employee>> employeesFilter(@RequestBody EmployeeFilter filter) {
        return ResponseBean.<Iterable<Employee>>builder()
                .data(this.employeeService.employeesFilter(filter))
                .message("Success")
                .build();
    }

}

