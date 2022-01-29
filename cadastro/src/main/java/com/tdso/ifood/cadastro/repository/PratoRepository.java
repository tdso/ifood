package com.tdso.ifood.cadastro.repository;

import java.util.List;

import com.tdso.ifood.cadastro.model.Prato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {

    @Query("SELECT p from Prato p JOIN p.restaurante r WHERE r.id = :id")
    List<Prato> findByPratoPeloCodRestaurante(@Param("id") Long id);

}