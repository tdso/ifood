package com.tdso.ifood.cadastro.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDtoValidator implements ConstraintValidator<ValidDto, Dto> {

    @Override
    public boolean isValid(Dto dto, ConstraintValidatorContext context) {
        return dto.isValid(context);
    }

}