package com.espark.adarsh.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class ApplicationResponseBean<T> implements Serializable {

    T data;
    String message;

    public ApplicationResponseBean() {
    }

    public ApplicationResponseBean(T data, String message) {
        this.data = data;
        this.message = message;
    }


}
