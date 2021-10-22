package com.espark.adarsh.service;

import com.espark.adarsh.entity.Person;
import com.espark.adarsh.util.ValidationUtil;

import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersonService {

    private Map<String, Person> dataStore = new ConcurrentHashMap<String, Person>();

    static ValidationUtil validationUtil = null;

    public void createPerson(String firstName, String lastName, String phoneNumber) {
        Person person = new Person(firstName, lastName, phoneNumber);
        validatePerson(person);
        checkIfPersonAlreadyExist(person);
        dataStore.put(generateId(person), person);
    }

    public Collection<Person> getAllPersons() {
        return dataStore.values();
    }

    private void checkIfPersonAlreadyExist(Person person) {
        if (dataStore.containsKey(generateId(person)))
            throw new RuntimeException("Person Already Exists");
    }

    private void validatePerson(Person person) {
        validationUtil = new ValidationUtil(person);
        validationUtil.validateFirstName();
        validationUtil.validateLastName();
        validationUtil.validatePhoneNumber();
    }

    private String generateId(Person person) {
        return Base64.getEncoder().encodeToString((person.getFirstName()+person.getLastName()).toLowerCase().getBytes());
    }
}
