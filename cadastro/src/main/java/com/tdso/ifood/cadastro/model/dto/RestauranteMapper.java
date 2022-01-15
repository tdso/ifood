package com.tdso.ifood.cadastro.model.dto;

import com.tdso.ifood.cadastro.model.Restaurante;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    public Restaurante toRestaurante(AdicionarRestauranteDto dto);

}