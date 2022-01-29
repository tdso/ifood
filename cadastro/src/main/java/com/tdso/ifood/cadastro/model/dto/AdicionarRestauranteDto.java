package com.tdso.ifood.cadastro.model.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tdso.ifood.cadastro.model.validator.Dto;
import com.tdso.ifood.cadastro.model.validator.ValidDto;

@ValidDto
public class AdicionarRestauranteDto implements Dto {

    @NotNull
    @NotEmpty
    public String proprietario;

    @Size(min = 3, max = 10)
    public String cnpj;

    public String nome;
    public LocalizacaoDto localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext cvc) {
        cvc.disableDefaultConstraintViolation();

        System.out.println(" ");
        System.out.println(" entrou ");
        if (nome.length() == 0 || nome.isBlank()) {
            cvc.buildConstraintViolationWithTemplate("Nome deve ser informado !!(uai)").addPropertyNode("nome")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}