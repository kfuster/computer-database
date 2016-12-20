package com.excilys.formation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.GenericValidator;

public class DateValidator implements ConstraintValidator<Date, String> {
    private String format;
    @Override
    public void initialize(Date constraintAnnotation) {
        this.format = constraintAnnotation.formatString();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value) || GenericValidator.isDate(value, format, true)) {
            return true;
        }
        return false;
    }
}