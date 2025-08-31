package org.ms.library.client.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {


    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone.isBlank()) return false;
        phone = phone.replaceAll("\\D", "");
        String regex = "^(?:[1-9]{2})(?:9[0-9]{8}|[2-8][0-9]{7})$";

        return phone.matches(regex);

    }
}
