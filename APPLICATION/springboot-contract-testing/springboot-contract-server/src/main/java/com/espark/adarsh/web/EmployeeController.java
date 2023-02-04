package com.espark.adarsh.web;

import com.espark.adarsh.entity.ApplicationResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<ApplicationResponseBean<List<Employee>>> getAllEmployee() {
        ApplicationResponseBean<List<Employee>> responseBean =
                new ApplicationResponseBean<>(employeeService.getAllEmployee(), "Data Fetch Successfully");
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<ApplicationResponseBean<Employee>> getEmployee(@PathVariable("id") Long id) {
        ApplicationResponseBean<Employee> responseBean =
                new ApplicationResponseBean<>(employeeService.getEmployee(id), "Data Fetch Successfully");
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }

    @PostMapping(path = "/employee",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApplicationResponseBean<Employee>> saveEmployee(@RequestBody Employee employee) {
        ApplicationResponseBean<Employee> responseBean =
                new ApplicationResponseBean<>(employeeService.saveEmployee(employee), "Operation Successfully");
        return ResponseEntity
                .status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }


    @DeleteMapping("/employee/{id}")
    public ResponseEntity<ApplicationResponseBean<Employee>> removeEmployee(@PathVariable("id") Long id) {
        ApplicationResponseBean<Employee> responseBean =
                new ApplicationResponseBean<>(employeeService.removeEmployee(id), "Operation Successfully");
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }


    @PutMapping("/employee/{id}")
    public ResponseEntity<ApplicationResponseBean<Employee>> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        ApplicationResponseBean<Employee> responseBean =
                new ApplicationResponseBean<>(employeeService.updateEmployee(id, employee), "Operation Successfully");
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }

    @PatchMapping("/employee/{id}")
    public ResponseEntity<ApplicationResponseBean<Employee>> updatePartialEmployee(@PathVariable("id") Long id, @RequestBody Map<String, Object> employee) {
        ApplicationResponseBean<Employee> responseBean =
                new ApplicationResponseBean<>(employeeService.updatePartialEmployee(id, employee), "Operation Successfully");
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBean);
    }

}
