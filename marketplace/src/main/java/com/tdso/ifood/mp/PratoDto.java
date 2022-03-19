package com.tdso.ifood.mp;

import java.math.BigDecimal;

import io.vertx.mutiny.sqlclient.Row;

public class PratoDto {

    public Long id;

    public String nome;
    public String descricao;
    public BigDecimal preco;

    public static PratoDto from(Row row) {

        PratoDto dto = new PratoDto();
        dto.descricao = row.getString("descricao");
        dto.nome = row.getString("nome");
        dto.id = row.getLong("id");
        dto.preco = row.getBigDecimal("preco");
        return dto;
    }

}
