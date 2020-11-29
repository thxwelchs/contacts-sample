package com.osci.contacts.util;

import com.osci.contacts.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<Phone, String> {
    private Phone phone;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.phone = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(this.phone.regexp());
    }
}