package com.espark.adarsh.exception;



import java.util.HashMap;
import java.util.Map;

public class EmployeeNotFoundException extends RuntimeException  {

    private Map<String, Object> extensions = new HashMap<>();

    public EmployeeNotFoundException(String message, Long id) {
        super(message);
        extensions.put("EmployeeNotFoundException ", id);
    }


    @Override
    public String toString() {
        return "EmployeeNotFoundException " + this.getMessage();
    }

}
