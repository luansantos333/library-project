package org.ms.library.client.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<ValidCEP, String> {

    @Override
    public boolean isValid(String CEP, ConstraintValidatorContext constraintValidatorContext) {

        if (CEP.isBlank()) return false;
        String regex = "^\\d{2}\\d{3}[-]\\d{3}$";

        return CEP.matches(regex);
    }
}
