package com.tdso.ifood.mp;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@Path("/compra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    @Channel("pedidos")
    Emitter<String> emitter;

    @POST
    // @Transactional
    public Response comprar(PedidoDto pedido) {

        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(pedido);
        emitter.send(json);

        return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

}