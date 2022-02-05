package com.tdso.ifood.cadastro.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { PaginaValidatorImpl.class })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface PaginaValidator {

    String message() default "o numero de paginas não pode ser ${qtd_paginas} - deve estar entre 3-100";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}