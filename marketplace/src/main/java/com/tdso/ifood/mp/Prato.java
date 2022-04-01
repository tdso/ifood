package com.tdso.ifood.mp;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Prato {

    public Long id;
    public String nome;
    public String descricao;
    public Restaurante restaurante;
    public BigDecimal preco;

    public static Multi<PratoDto> findAll(PgPool pgPool) {

        Uni<RowSet<Row>> preparedQuery = pgPool.query("select * from prato").execute();
        return uniToMulti(preparedQuery);
    }

    public static Uni<PratoDto> findById(PgPool pgPool, Long id) {

        Tuple param = Tuple.of(id);
        return pgPool.preparedQuery("SELECT * FROM prato WHERE prato.id = $1").execute(param).map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDto.from(iterator.next()) : null);
    }

    public static Multi<PratoDto> findAll(PgPool pgPool, Long idRestaurante) {

        System.out.println("2 metodo");

        Tuple param = Tuple.of(idRestaurante);
        return uniToMulti(pgPool.preparedQuery("SELECT * FROM prato WHERE prato.restaurante_id = $1 ORDER BY nome ASC")
                .execute(param));
    }

    private static Multi<PratoDto> uniToMulti(Uni<RowSet<Row>> res) {
        return res.onItem().transformToMulti(set -> Multi.createFrom().items(() -> {
            return StreamSupport.stream(set.spliterator(), false);
        })).map(item -> PratoDto.from(item));
    }

}