package it.sevenbits.p2_base_spring.web.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target( { ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusConstraint {

    String message() default "Incorrect status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}