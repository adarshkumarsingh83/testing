package com.espark.adarsh.service;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final String hashReference = "Employee";

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Employee> hashOperations;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = new LinkedList<>();
        Map<Long, Employee> employeeMap = hashOperations.entries(hashReference);
        if (employeeMap == null) {
            this.employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
            return employeeList;
        } else {
            employeeList.addAll(employeeMap.values());
        }
        return employeeList;
    }

    public Employee getEmployee(Long id) {
        Employee employee = hashOperations.get(hashReference, id);
        if (employee == null) {
            return this.employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("employee not found"));
        }
        return employee;
    }

    public Employee removeEmployee(Long id) {
        hashOperations.delete(hashReference, id);
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found"));
        this.employeeRepository.deleteById(id);
        return employee;
    }

    public Employee saveEmployee(Employee employee) {
        hashOperations.putIfAbsent(hashReference, employee.getId(), employee);
        return this.employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        hashOperations.put(hashReference, employee.getId(), employee);
        return this.employeeRepository.save(employee);
    }
}
