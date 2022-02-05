package com.tdso.ifood.cadastro.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tdso.ifood.cadastro.model.dto.LivroDto;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class PaginaValidatorImpl implements ConstraintValidator<PaginaValidator, LivroDto> {

    @Override
    public boolean isValid(LivroDto livro, ConstraintValidatorContext context) {

        final var validatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
        validatorContext.addExpressionVariable("qtd_paginas", livro.paginas);
        return (livro.paginas >= 3 && livro.paginas <= 100);
    }

}