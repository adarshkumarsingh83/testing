package com.espark.adarsh.service;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.espark.adarsh.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = new LinkedList<>();
        this.employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
        return employeeList;
    }

    public Employee getEmployee(Long id) throws EmployeeNotFoundException{
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found", id));
    }

    public Employee removeEmployee(Long id) throws EmployeeNotFoundException{
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found", id));
        this.employeeRepository.deleteById(id);
        return employee;
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        return this.employeeRepository.save(employee);
    }


    public Employee updatePartialEmployee( Long id, Map<String, Object> employee) throws EmployeeNotFoundException{
        final Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employee.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Employee.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, employeeOptional.get(), value);
            });
            return this.employeeRepository.save(employeeOptional.get());
        }
        return employeeOptional.orElseThrow(() -> new EmployeeNotFoundException("employee not found", id));
    }


    public Iterable<Employee> employeesFilter(EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)
            spec = bySalary(filter.getSalary());
        if (filter.getId() != null)
            spec = (spec == null ? byId(filter.getId()) : spec.and(byId(filter.getId())));
        if (filter.getCarrier() != null)
            spec = (spec == null ? byCarrier(filter.getCarrier()) :
                    spec.and(byCarrier(filter.getCarrier())));
        if (spec != null)
            return employeeRepository.findAll(spec);
        else
            return employeeRepository.findAll();
    }

    private Specification<Employee> bySalary(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
    }

    private Specification<Employee> byId(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("id"));
    }

    private Specification<Employee> byCarrier(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("carrier"));
    }

}
