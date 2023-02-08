package com.espark.adarsh.exception;

import com.espark.adarsh.entity.ApplicationResponseBean;
import com.espark.adarsh.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApplicationResponseBean> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        return new ResponseEntity<>(new ApplicationResponseBean<Employee>(null, exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationResponseBean> handleException(Exception exception) {
        return new ResponseEntity<>(new ApplicationResponseBean<Employee>(null, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
