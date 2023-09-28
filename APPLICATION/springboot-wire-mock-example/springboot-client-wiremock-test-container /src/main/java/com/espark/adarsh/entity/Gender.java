package com.espark.adarsh.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE");

    String value;

    Gender(String value){
        this.value=value;
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static Gender gender(String value) {
        return (!value.isEmpty()) && value.equals(Gender.MALE.value) ? Gender.MALE: Gender.FEMALE;
    }

}

