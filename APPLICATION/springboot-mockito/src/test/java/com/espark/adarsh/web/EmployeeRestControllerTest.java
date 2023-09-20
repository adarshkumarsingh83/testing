package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.entity.Gender;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.espark.adarsh.respository.EmployeeRepository;
import com.espark.adarsh.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeRestControllerTest {


    @InjectMocks
    EmployeeRestController employeeRestController;


    @Mock
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;


    EmployeeBean employeeBean;

    ResponseBean<Employee> responseBean;

    EmployeeFilter filter;

    @BeforeEach
    public void init() {
        employeeBean = new EmployeeBean(100L, "firstName", "lastName", "career", 10L, LocalDate.now(), Gender.MALE, new HashMap<>(),List.<String>of("123","345"));
        responseBean = ResponseBean.<Employee>builder().data(employeeBean.getEmployee()).build();
        filter = new EmployeeFilter();
        filter.setSalary(new FilterField(){
            {
                setOperator("gt");
                setValue("5");
            }
        });
    }

    @Test
    public void testGetAllEmployee() {
        employeeRestController.getAllEmployee();
        Mockito.verify(employeeService, Mockito.atLeastOnce()).getAllEmployee();
    }

    @Test
    public void testGetEmployee() {
        employeeRestController.getEmployee(Mockito.anyLong());
        Mockito.verify(employeeService, Mockito.atLeastOnce()).getEmployee(Mockito.anyLong());
    }

    @Test
    public void testDeleteEmployee() {
        employeeRestController.removeEmployee(Mockito.anyLong());
        Mockito.verify(employeeService, Mockito.atLeastOnce()).removeEmployee(Mockito.anyLong());
    }

    @Test
    public void testSaveEmployee() {
        when(employeeRestController.saveEmployee(employeeBean)).thenReturn(responseBean);
        employeeRestController.saveEmployee(employeeBean);
        Mockito.verify(employeeService, Mockito.atLeastOnce()).saveEmployee(employeeBean.getEmployee());
    }

    @Test
    public void testUpdateEmployee() {
        when(employeeRestController.updateEmployee(employeeBean)).thenReturn(responseBean);
        employeeRestController.updateEmployee(employeeBean);
        Mockito.verify(employeeService, Mockito.atLeastOnce()).updateEmployee(employeeBean.getId(),employeeBean.getEmployee());
    }

    @Test
    public void testEmployeeFilter() {
        ResponseBean<Iterable<Employee>> responseBean = ResponseBean.<Iterable<Employee>>builder()
                .data(List.of(employeeBean.getEmployee()))
                .build();
        when(employeeRestController.employeesFilter(filter)).thenReturn(responseBean);
        employeeRestController.employeesFilter(filter);
        Mockito.verify(employeeService, Mockito.atLeastOnce()).employeesFilter(filter);
    }

}
