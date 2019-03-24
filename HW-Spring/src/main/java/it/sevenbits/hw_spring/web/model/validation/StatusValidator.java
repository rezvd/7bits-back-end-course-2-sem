package it.sevenbits.p2_base_spring.web.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class StatusValidator implements ConstraintValidator<StatusConstraint, String> {
    private List<String> statuses = new ArrayList<>();

    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        statuses.add("inbox");
        statuses.add("done");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return statuses.contains(s);
    }
}