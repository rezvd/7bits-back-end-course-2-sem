package it.sevenbits.hwspring.core.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class for validation status constraint
 */
public class StatusConstraintValidator implements ConstraintValidator<StatusConstraint, String> {

    /**
     * Initialization of validator
     * @param constraintAnnotation is status constraint
     */
    @Override
    public void initialize(final StatusConstraint constraintAnnotation) {
    }

    /**
     * Validates status constraint
     * @param s is status, which should be checked
     * @param constraintValidatorContext is context
     * @return true, if the status is valid, otherwise return false
     */
    @Override
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return StatusValidator.isValid(s);
    }
}