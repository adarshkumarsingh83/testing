package com.espark.adarsh.util;

import com.espark.adarsh.entity.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReaderUtil {

    static final ObjectMapper objectMapper = new ObjectMapper();
    static File employeesResponseFile = null;
    static File employeeResponseFile = null;
    static File employeeSaveResponseFile = null;

    static File employeeUpdateResponseFile = null;
    static File employeeFilterResponseFile = null;

    static {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            employeesResponseFile = new ClassPathResource("/store/employeesResponse.json").getFile();
            employeeResponseFile = new ClassPathResource("/store/employeeResponse.json").getFile();
            employeeSaveResponseFile = new ClassPathResource("/store/employeeSaveResponse.json").getFile();
            employeeUpdateResponseFile = new ClassPathResource("/store/employeeUpdateResponse.json").getFile();
            employeeFilterResponseFile = new ClassPathResource("/store/filterEmployeeResponse.json").getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Employee> getEmployeesResponseData() throws IOException {
        ArrayList<Employee> response =  objectMapper.readValue(employeesResponseFile, new TypeReference<ArrayList<Employee>>() {
        });
        return response;
    }

    public static Employee getEmployeeResponseData() throws IOException {
        Employee response =  objectMapper.readValue(employeeResponseFile, new TypeReference<Employee>() {
        });
        return response;
    }

    public static Employee getEmployeeSaveResponseData() throws IOException {
        Employee response =  objectMapper.readValue(employeeSaveResponseFile, new TypeReference<Employee>() {
        });
        return response;
    }

    public static Employee getEmployeeUpdateResponseData() throws IOException {
        Employee response =  objectMapper.readValue(employeeUpdateResponseFile, new TypeReference<Employee>() {
        });
        return response;
    }

    public static List<Employee> getEmployeeFilterResponseData() throws IOException {
        List<Employee> response =  objectMapper.readValue(employeeFilterResponseFile, new TypeReference<List<Employee>>() {
        });
        return response;
    }
}
