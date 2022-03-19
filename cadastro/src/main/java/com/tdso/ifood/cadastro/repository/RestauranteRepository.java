package com.tdso.ifood.cadastro.repository;

import java.util.List;

import com.tdso.ifood.cadastro.model.Restaurante;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query("SELECT r from Restaurante r WHERE r.cnpj = :cnpj and r.id = :id")
    List<Restaurante> findByRestauranteCNPJ(@Param("cnpj") String cnpj, @Param("id") Long id);

}