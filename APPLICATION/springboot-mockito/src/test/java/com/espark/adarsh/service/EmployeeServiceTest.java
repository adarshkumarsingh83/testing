package com.espark.adarsh.service;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.espark.adarsh.respository.EmployeeRepository;
import com.espark.adarsh.util.DataReaderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    EmployeeFilter filter;


    @BeforeEach
    public void init() {
        filter = new EmployeeFilter();
        filter.setSalary(new FilterField() {
            {
                setOperator("gt");
                setValue("5");
            }
        });
    }

    @Test
    public void testGetAllEmployee() throws IOException {
        List<Employee> list = DataReaderUtil.getEmployeesResponseData();
        when(employeeRepository.findAll()).thenReturn(list);
        ResponseBean<List<Employee>> responseBean = employeeService.getAllEmployee();
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAll();
        Assertions.assertNotNull(responseBean.getData());
    }

    @Test
    public void testGetEmployee() throws IOException {
        Long id = 1L;
        Optional<Employee> optional = Optional.of(DataReaderUtil.getEmployeeResponseData());
        when(employeeRepository.findById(id)).thenReturn(optional);
        ResponseBean<Employee> responseBean = employeeService.getEmployee(id);
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findById(id);
        Assertions.assertNotNull(responseBean.getData());
    }

    @Test()
    public void testGetEmployeeNotFound() {
        Long id = 100L;
        Optional<Employee> optional = Optional.empty();
        when(employeeRepository.findById(id)).thenReturn(optional);
        EmployeeNotFoundException employeeNotFoundException = assertThrows(EmployeeNotFoundException.class,
                () -> {
                    employeeService.getEmployee(id);
                });
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findById(id);
        assertNotNull(employeeNotFoundException);
    }


    @Test
    public void saveEmployee() throws IOException {
        Employee employee = DataReaderUtil.getEmployeeSaveResponseData();
        when(employeeRepository.save(employee)).thenAnswer(new Answer<Employee>() {
            @Override
            public Employee answer(InvocationOnMock invocation) throws Throwable {
                //get the arguments passed to mock
                Object[] args = invocation.getArguments();
                //get the mock
                Object mock = invocation.getMock();
                //return the result
                return (Employee) args[0];
            }
        });
        ResponseBean<Employee> responseBean = employeeService.saveEmployee(employee);
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).save(employee);
        Mockito.verify(employeeRepository, Mockito.timeout(10)).save(employee);
        assertNotNull(responseBean.getData());
    }

    @Test
    public void updateEmployee() throws IOException {
        Employee employee = DataReaderUtil.getEmployeeUpdateResponseData();
        when(employeeRepository.save(employee)).thenAnswer(new Answer<Employee>() {
            @Override
            public Employee answer(InvocationOnMock invocation) throws Throwable {
                //get the arguments passed to mock
                Object[] args = invocation.getArguments();
                //get the mock
                Object mock = invocation.getMock();
                //return the result
                return (Employee) args[0];
            }
        });
        ResponseBean<Employee> responseBean = employeeService.updateEmployee(employee.getId(), employee);
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).save(employee);
        Mockito.verify(employeeRepository, Mockito.timeout(10)).save(employee);
        assertNotNull(responseBean.getData());
    }


    @Test
    public void setFilterEmployee() throws IOException {
        List<Employee> response = DataReaderUtil.getEmployeeFilterResponseData();
        when(employeeRepository.findAll(Mockito.any())).thenReturn(response);
        ResponseBean<Iterable<Employee>> responseBean = employeeService.employeesFilter(filter);
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAll(Mockito.any());
        Mockito.verify(employeeRepository, Mockito.timeout(10)).findAll(Mockito.any());
        assertNotNull(responseBean.getData());
    }
}
