package br.itb.projeto.vitalususPlus.validation;

import br.itb.projeto.vitalususPlus.validation.constraints.CREF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CrefValidation implements ConstraintValidator<CREF, String> {
    @Override
    public void initialize(CREF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String placa = value == null ? "": value;
        return placa.matches("[0-9]{6}-[GP]/[A-Z]{2}(-[A-Z]{2})?(-[A-Z]{2}-[A-Z]{2})?");
    }
}