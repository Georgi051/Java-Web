package com.example.residentevil.web.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateBeforeValidatorImpl implements ConstraintValidator<LocalDateBefore, LocalDate> {
    private LocalDate date = LocalDate.now();


    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        boolean valid = true;
        if (value != null) {
            if (!value.isBefore(date)) {
                valid = false;
            }
        }
        return valid;
    }
}
