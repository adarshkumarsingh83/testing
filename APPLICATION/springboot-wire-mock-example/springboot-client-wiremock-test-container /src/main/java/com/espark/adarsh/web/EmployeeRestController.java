package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;

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
    public ResponseBean<List<EmployeeBean>> getAllEmployeege() {
       return ResponseBean.<List<EmployeeBean>>builder()
                .data(this.employeeService.getAllEmployeeBean())
                .message("Successful ")
                .build();
    }

    @GetMapping("/employee/{id}")
    public ResponseBean<EmployeeBean> getEmployee(@PathVariable("id") Long id) {
        return ResponseBean.<EmployeeBean>builder()
                .data(this.employeeService.getEmployeeBean(id))
                .message("Successful")
                .build();
    }

    @PostMapping("/employee")
    public ResponseBean<EmployeeBean> saveEmployee(@RequestBody @Valid EmployeeBean employeeBean) throws JsonProcessingException {
        return ResponseBean.<EmployeeBean>builder()
                .data(this.employeeService.saveEmployeeBean(employeeBean))
                .message("Successful")
                .build();
    }

   @DeleteMapping("/employee/{id}")
    public ResponseBean<EmployeeBean> removeEmployee(@PathVariable("id") Long id) {
       return ResponseBean.<EmployeeBean>builder()
               .data(this.employeeService.removeEmployeeBean(id))
               .message("Successful")
               .build();
    }



    @PutMapping("/employee")
    public ResponseBean<EmployeeBean> updateEmployee(@RequestBody @Valid EmployeeBean employeeBean) {
        return ResponseBean.<EmployeeBean>builder()
                .data(this.employeeService.updateEmployeeBean(employeeBean.getId(),employeeBean))
                .message("Successful")
                .build();
    }


}

