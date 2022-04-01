package com.tdso.ifood.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.mutiny.pgclient.PgPool;

@ApplicationScoped
public class RestauranteCadastrado {

    @Inject
    PgPool pgpool;

    @Incoming("restaurantes")
    public void receberRestaurante(String json) {
        System.out.println(" ");
        System.out.println(" ************* ");
        System.out.println(json);
        System.out.println(" ************* ");
        Jsonb create = JsonbBuilder.create();
        Restaurante restaurante = create.fromJson(json, Restaurante.class);
        restaurante.persist(pgpool);
    }

}