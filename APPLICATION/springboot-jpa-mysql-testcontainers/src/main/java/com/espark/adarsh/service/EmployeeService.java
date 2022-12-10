package com.espark.adarsh.service;

import com.espark.adarsh.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long empId);

    Employee updateEmployee(Long empId, Employee employeeDetails);

    Employee updatePartialEmployee(@PathVariable("id") Long id, Map<String, Object> employee);

    Employee deleteEmployee(Long empId);
}
