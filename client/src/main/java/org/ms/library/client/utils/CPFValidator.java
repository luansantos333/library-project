package org.ms.library.client.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {


    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        if (cpf == null)  return false;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        return true;

    }
}
