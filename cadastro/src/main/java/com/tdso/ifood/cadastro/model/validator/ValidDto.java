package com.tdso.ifood.cadastro.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { ValidDtoValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface ValidDto {

    String message() default "{com.tdso.ifood.cadastro.model.validator.ValidDto.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}