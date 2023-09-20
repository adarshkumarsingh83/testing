package com.espark.adarsh.service;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.espark.adarsh.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${application.success.msg}")
    String successMessage;





    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseBean<List<Employee>> getAllEmployee() {
        List<Employee> employeeList = new LinkedList<>();
        this.employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
        return ResponseBean.<List<Employee>>builder()
                .data(employeeList)
                .message(successMessage)
                .build();

    }

    public ResponseBean<Employee> getEmployee(Long id) throws EmployeeNotFoundException {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found", id));
        return ResponseBean.<Employee>builder().data(employee)
                .message(successMessage)
                .build();
    }

    public ResponseBean<Employee> removeEmployee(Long id) throws EmployeeNotFoundException {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found", id));
        this.employeeRepository.deleteById(id);
        return ResponseBean.<Employee>builder()
                .data(employee)
                .message(successMessage)
                .build();
    }

    public ResponseBean<Employee> saveEmployee(Employee employee) {

        return ResponseBean.<Employee>builder()
                .data(this.employeeRepository.save(employee))
                .message(successMessage)
                .build();
    }

    public ResponseBean<Employee> updateEmployee(Long id, Employee employee) {
        return ResponseBean.<Employee>builder()
                .data(this.employeeRepository.save(employee))
                .message(successMessage)
                .build();
    }


    public ResponseBean<Employee> updatePartialEmployee( Long id, Map<String, Object> employee)
            throws EmployeeNotFoundException {
        final Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employee.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Employee.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, employeeOptional.get(), value);
            });

            return ResponseBean.<Employee>builder()
                    .data(this.employeeRepository.save(employeeOptional.get()))
                    .message(successMessage)
                    .build();
        }
        throw new EmployeeNotFoundException("employee not found", id);
    }


    public ResponseBean<Iterable<Employee>> employeesFilter(EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)
            spec = bySalary(filter.getSalary());
        if (filter.getId() != null)
            spec = (spec == null ? byId(filter.getId()) : spec.and(byId(filter.getId())));
        if (filter.getCarrier() != null)
            spec = (spec == null ? byCarrier(filter.getCarrier()) :
                    spec.and(byCarrier(filter.getCarrier())));
        if (spec != null)
        return ResponseBean.<Iterable<Employee>>builder()
                .data(employeeRepository.findAll(spec))
                .message(successMessage)
                .build();
        else
            return ResponseBean.<Iterable<Employee>>builder()
                    .data(employeeRepository.findAll())
                    .message(successMessage)
                    .build();
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
