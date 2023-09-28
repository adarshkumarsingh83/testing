package com.espark.adarsh.service;

import com.espark.adarsh.bean.EmployeeBean;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.server.url}")
    String apiUrl;

    public List<EmployeeBean> getAllEmployeeBean() {
        Map<String, String> headers = new HashMap<>() {
            {
              put("Accept","application/json");
            }
        };
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<ResponseBean<List<EmployeeBean>>> response = restTemplate.exchange(apiUrl + "/employees",
                HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseBean<List<EmployeeBean>>>() {
        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getData();
        }
        return null;
    }

    public EmployeeBean getEmployeeBean(Long id) throws EmployeeNotFoundException {
        Map<String, String> headers = new HashMap<>() {
            {
                put("Accept","application/json");
            }
        };
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<ResponseBean<EmployeeBean>> response = restTemplate.exchange(apiUrl + "/employee/"+id,
                HttpMethod.GET, httpEntity
                , new ParameterizedTypeReference<ResponseBean<EmployeeBean>>() {
        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getData();
        }
        return null;
    }

    public EmployeeBean removeEmployeeBean(Long id) throws EmployeeNotFoundException {
     return  null;
    }

    public EmployeeBean saveEmployeeBean(EmployeeBean employee) {
        return  null;
    }

    public EmployeeBean updateEmployeeBean(Long id, EmployeeBean employee) {
        return  null;
    }


    public EmployeeBean updatePartialEmployeeBean(Long id, Map<String, Object> employee) throws EmployeeNotFoundException {
        return  null;
    }

}
