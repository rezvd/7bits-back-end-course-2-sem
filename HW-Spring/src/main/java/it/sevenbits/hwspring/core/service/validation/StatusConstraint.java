package it.sevenbits.hwspring.core.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint for status field in task
 */
@Documented
@Constraint(validatedBy = StatusConstraintValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusConstraint {

    /**
     * Sets default message for validation error
     * @return default message for validation error
     */
    String message() default "Incorrect status";

    /**
     * Groups class for validation
     * @return empty array by default
     */
    Class<?>[] groups() default {};

    /**
     * Provides additional metadata information (not needed here, so it's empty)
     * @return empty array by default
     */
    Class<? extends Payload>[] payload() default {};
}