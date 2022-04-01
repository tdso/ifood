package com.tdso.ifood.mp;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@Path("/compra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    PgPool pgPool;

    @Inject
    @Channel("pedidos")
    Emitter<String> emitter;

    @POST
    // @Transactional
    @Path("/realizar-pedido")
    public Response realizarPedido(String nome) {

        // busca Prato no Carrinho
        List<PratoCarrinho> carrinho = PratoCarrinho.findCarrinhoByClient(pgPool, nome);

        List<PratoPedidoDTO> pratos = carrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());

        RestauranteDTO restauranteDto = new RestauranteDTO("nome restaurante mokado");

        PedidoRealizadoDTO pedRealizado = new PedidoRealizadoDTO();
        pedRealizado.setCliente(nome);
        pedRealizado.setRestaurante(restauranteDto);
        pedRealizado.setPratos(pratos);

        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(pedRealizado);
        emitter.send(json);

        PratoCarrinho.delete(pgPool, nome);

        return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

    @GET
    @Path("/recuperar-carrinho/{nome}")
    public Uni<List<PratoCarrinho>> getCarrinho(@PathParam("nome") String nome) {

        return PratoCarrinho.findCarrinhoByClient(pgPool, nome);
    }

    @POST
    @Path("/incluir-carrinho")
    public Uni<Boolean> addCarrinho(PratoCarrinho pratoCarrinho) {

        return PratoCarrinho.savePratoCarrinho(pgPool, pratoCarrinho);

        // return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

    private PratoPedidoDTO from(PratoCarrinho pratoCarrinho) {
        PratoDto dto = Prato.findById(pgPool, pratoCarrinho.prato).await().indefinitely();
        return new PratoPedidoDTO(dto.nome, dto.descricao, dto.preco);
    }

}