package it.sevenbits.hwspring.core.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusConstraintValidator implements ConstraintValidator<StatusConstraint, String> {

    @Override
    public void initialize(final StatusConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return StatusValidator.isValid(s);
    }
}