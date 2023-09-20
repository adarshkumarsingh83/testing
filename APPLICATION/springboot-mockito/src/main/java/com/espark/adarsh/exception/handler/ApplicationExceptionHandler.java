package com.espark.adarsh.exception.handler;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @Value("${application.failure.msg}")
    String failureMessage;


    @org.springframework.web.bind.annotation.ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseBean<EmployeeBean> handleEmployeeNotFoundException(EmployeeNotFoundException e){
       return ResponseBean.<EmployeeBean>builder()
               .errors(List.of(e.getMessage()))
                .message(failureMessage)
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseBean<EmployeeBean> handleException(Exception e){
        return ResponseBean.<EmployeeBean>builder()
                .errors(List.of(e.getMessage()))
                .message(failureMessage)
                .build();
    }
}
