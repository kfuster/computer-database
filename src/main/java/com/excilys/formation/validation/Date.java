package com.excilys.formation.validation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface Date {
    String formatString() default "yyyy-MM-dd" ;
    String message() default "Date not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}