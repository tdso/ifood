package com.tdso.ifood.cadastro.model.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;

public interface Dto {

    default boolean isValid(ConstraintValidatorContext cvc) {
        return true;
    }

}