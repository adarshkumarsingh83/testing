package com.espark.adarsh.service;

import com.espark.adarsh.dao.EmployeeRepository;
import com.espark.adarsh.model.Employee;
import com.espark.adarsh.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;


    public Response saveEmployee(@RequestBody Employee employee) {
        repository.save(employee);
        return new Response(employee.getId() + " inserted", Boolean.TRUE);
    }


    public Response getEmployees() {
        List<Employee> employees = repository.findAll();
        return new Response("record counts : " + employees.size(), Boolean.TRUE);
    }
}
