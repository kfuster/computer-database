package com.excilys.formation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface representing the DateAnterior annotation.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateAnteriorValidator.class)
public @interface DateAnterior {
    /**
     * Return the message parameter.
     * @return a String
     */
    String message() default "Date not anterior";

    /**
     * Return the groups parameter.
     * @return a Class<?> array
     */
    Class<?>[] groups() default { };

    /**
     * Return the payload parameter.
     * @return a Class<? extends Payload> array
     */
    Class<? extends Payload>[] payload() default { };
}