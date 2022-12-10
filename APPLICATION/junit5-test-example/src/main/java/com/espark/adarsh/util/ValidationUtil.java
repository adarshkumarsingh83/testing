package com.espark.adarsh.util;

import com.espark.adarsh.entity.Person;

public class ValidationUtil {

    Person person;

    public ValidationUtil(Person person) {
        this.person = person;
    }

    public void validateFirstName() {
        if (this.person.getFirstName().isBlank())
            throw new RuntimeException("First Name Cannot be null or empty");
    }

    public void validateLastName() {
        if (this.person.getLastName().isBlank())
            throw new RuntimeException("Last Name Cannot be null or empty");
    }

    public void validatePhoneNumber() {
        if (this.person.getPhoneNumber().isBlank()){
            throw new RuntimeException("Phone Name Cannot be null or empty");
        }

        if (this.person.getPhoneNumber().length() != 10) {
            throw new RuntimeException("Phone Number Should be 10 Digits Long");
        }
        if (!this.person.getPhoneNumber().matches("\\d+")) {
            throw new RuntimeException("Phone Number Contain only digits");
        }
        if (!this.person.getPhoneNumber().startsWith("0")) {
            throw new RuntimeException("Phone Number Should Start with 0");
        }
    }
}
