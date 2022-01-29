package com.tdso.ifood.cadastro.repository;

import com.tdso.ifood.cadastro.model.Restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}