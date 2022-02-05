package com.tdso.ifood.cadastro.service;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

import com.tdso.ifood.cadastro.model.dto.LivroDto;
import com.tdso.ifood.cadastro.model.dto.groups.ValidationGroups;

@ApplicationScoped
public class LivroService {

    public void ValidaLivro(@Valid LivroDto livroDto) {

    }

    // regra especifica para PUT
    public void ValidaLivroCustomGroup(@Valid @ConvertGroup(to = ValidationGroups.Put.class) LivroDto livroDto) {

    }

}