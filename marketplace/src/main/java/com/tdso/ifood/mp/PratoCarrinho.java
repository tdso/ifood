package com.tdso.ifood.mp;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class PratoCarrinho {

    public String usuario;

    public Long prato;

    public PratoCarrinho() {
    }

    public PratoCarrinho(final Row item) {
        usuario = item.getString("usuario");
        prato = item.getLong("prato");
    }

    // public static List<PratoCarrinho> findCarrinhoByClient(PgPool pgPool, String
    // nome) {
    public static Uni<List<PratoCarrinho>> findCarrinhoByClient(PgPool pgPool, String nome) {

        System.out.println("param nome = " + nome);
        // List<PratoCarrinho> list = new ArrayList<>();
        Tuple param = Tuple.of(nome);

        return pgPool.preparedQuery("select * from pratocarrinho where usuario = $1").execute(param).onItem()
                .transform(pgRowSet -> {
                    System.out.println("pgRowSet = " + pgRowSet.size());
                    List<PratoCarrinho> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(toPratoCarrinho(row));
                    }
                    return list;
                });

        // return (Multi<PratoCarrinho>) pgPool
        // .preparedQuery("select usuario, prato from pratocarrinho where
        // pratocarrinho.usuario = $1")
        // .execute(param).map(pgRowSet -> uniToMulti(pgRowSet));

        // System.out.println("dentro do map");
        // for (Row row : pgRowSet) {
        // System.out.println("dentro do for");
        // System.out.println("iterando lista = " + row.getString(0));
        // System.out.println("iterando lista = " + row.getString(1));
        // list.add(toPratoCarrinho(row));
        // }
        // return list;

        // });
        // .transform(pgRowSet -> {
        // // List<PratoCarrinho> list = new ArrayList<>(pgRowSet.size());
        // for (Row row : pgRowSet) {
        // System.out.println("iterando lista = " + row.getString(0));
        // System.out.println("iterando lista = " + row.getString(1));
        // list.add(toPratoCarrinho(row));
        // }
        // return list;
        // });
        // System.out.println("list = "+list.toString());return list;

    }

    private static PratoCarrinho toPratoCarrinho(final Row row) {
        final PratoCarrinho pc = new PratoCarrinho(row);
        System.out.println("pc = " + pc.toString());
        return pc;
    }

    public static Uni<Boolean> savePratoCarrinho(final PgPool pgPool, final PratoCarrinho pratoCarrinho) {
        final Tuple param = Tuple.of(pratoCarrinho.usuario, pratoCarrinho.prato);
        return pgPool.preparedQuery("insert into pratocarrinho(usuario, prato) values ($1,$2)").execute(param).onItem()
                .transform(pgRowSet -> pgRowSet.rowCount() == 1);
        // .map(pgRowSet -> pgRowSet.iterator().next().getLong("prato"));

    }

    // private static Multi<List<PratoCarrinho>> uniToMulti(final RowSet<Row>
    // pgRowSet) {
    // return pgRowSet.toMulti().map(item -> new PratoCarrinho(item)).map(item ->
    // Collectors.toList());

    // // return pgRowSet.map().transformToMulti(set -> Multi.createFrom().items(()
    // ->
    // // {
    // // return StreamSupport.stream(set.spliterator(), false);
    // // })).map(item -> new PratoCarrinho(item));
    // }

    public static Uni<Boolean> delete(final PgPool pgPool, final String nome) {
        final Tuple param = Tuple.of(nome);
        return pgPool.preparedQuery("delete from pratocarrinho where usuario = $1").execute(param).onItem()
                .transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }
}