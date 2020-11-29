package com.osci.contacts.annotation;

import com.osci.contacts.util.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "올바른 휴대폰 양식이 아닙니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String regexp() default "(^02.{0}|^01.{1}|[0-9]{3})-([0-9]+)-([0-9]{4})";
}