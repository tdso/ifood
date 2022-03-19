package com.tdso.ifood.mp;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class RestauranteCadastrado {

    @Incoming("restaurantes")
    public void receberRestaurante(String json) {
        System.out.println(" ");
        System.out.println(" ************* ");
        System.out.println(json);
        System.out.println(" ************* ");
    }

}