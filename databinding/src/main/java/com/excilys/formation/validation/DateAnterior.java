package com.excilys.formation.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateAnteriorValidator.class)
public @interface DateAnterior {
    /**
     * Return the message parameter.
     *
     * @return a String
     */
    String message() default "Date not anterior";
}