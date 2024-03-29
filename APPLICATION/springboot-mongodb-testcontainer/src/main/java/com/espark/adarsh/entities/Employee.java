package com.espark.adarsh.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Employee {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Employee[id=%s, firstName='%s', lastName='%s']", id,
                firstName, lastName);
    }

}