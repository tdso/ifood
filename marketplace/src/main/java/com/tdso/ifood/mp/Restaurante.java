package com.tdso.ifood.mp;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

public class Restaurante {

    public Long id;
    public String nome;
    public Localizacao localizacao;

    public void persist(PgPool pgpool) {
        Tuple param = Tuple.of(localizacao.id, localizacao.latitude, localizacao.longitude);
        pgpool.preparedQuery("insert into localizacao (id, latitude, longitude) values ($1,$2,$3)").execute(param)
                .await().indefinitely();

        param = Tuple.of(id, nome, localizacao.id);
        pgpool.preparedQuery("insert into restaurante(id, nome, localizacao_id) values ($1,$2,$3)").execute(param)
                .await().indefinitely();

    }

}