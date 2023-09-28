package com.espark.adarsh.filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;



@Data
public class FilterField {
    private String operator;
    private String value;

    public Predicate generateCriteria(CriteriaBuilder builder, Path field) {
        try {
            int v = Integer.parseInt(value);
            switch (operator) {
                case "lt": return builder.lt(field, v);
                case "le": return builder.le(field, v);
                case "gt": return builder.gt(field, v);
                case "ge": return builder.ge(field, v);
                case "eq": return builder.equal(field, v);
            }
        } catch (NumberFormatException e) {
            switch (operator) {
                case "endsWith": return builder.like(field, "%" + value);
                case "startsWith": return builder.like(field, value + "%");
                case "contains": return builder.like(field, "%" + value + "%");
                case "eq": return builder.equal(field, value);
            }
        }

        return null;
    }
}