package com.tdso.ifood.cadastro.model.dto;

import com.tdso.ifood.cadastro.model.Prato;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PratoMapper {


    public Prato toPrato(AdicionarPratoDto dto);


    
}