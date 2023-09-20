package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseBean<List<Employee>> getAllEmployee() {
        return this.employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{id}")
    public ResponseBean<Employee> getEmployee(@PathVariable("id") Long id) {
        return this.employeeService.getEmployee(id);
    }

    @PostMapping("/employee")
    public ResponseBean<Employee> saveEmployee(@RequestBody @Valid EmployeeBean employeeBean) {
        return this.employeeService.saveEmployee(employeeBean.getEmployee());
    }

    @DeleteMapping("/employee/{id}")
    public ResponseBean<Employee> removeEmployee(@PathVariable("id") Long id) {
        return this.employeeService.removeEmployee(id);
    }


    @PutMapping("/employee")
    public ResponseBean<Employee> updateEmployee(@RequestBody @Valid EmployeeBean employeeBean) {
        return this.employeeService.updateEmployee(employeeBean.getId(), employeeBean.getEmployee());
    }


    @PostMapping("/employee/filter")
    public ResponseBean<Iterable<Employee>> employeesFilter(@RequestBody EmployeeFilter filter) {
        return this.employeeService.employeesFilter(filter);
    }

}

