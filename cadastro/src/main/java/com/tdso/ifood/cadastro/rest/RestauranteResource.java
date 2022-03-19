package com.tdso.ifood.cadastro.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tdso.ifood.cadastro.model.Prato;
import com.tdso.ifood.cadastro.model.Restaurante;
import com.tdso.ifood.cadastro.model.dto.AdicionarPratoDto;
import com.tdso.ifood.cadastro.model.dto.AdicionarRestauranteDto;
import com.tdso.ifood.cadastro.model.dto.PratoMapper;
import com.tdso.ifood.cadastro.model.dto.RestauranteMapper;
import com.tdso.ifood.cadastro.repository.PratoRepository;
import com.tdso.ifood.cadastro.repository.RestauranteRepository;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    PratoMapper pratoMapper;

    @Inject
    @Channel("restaurantes")
    Emitter<String> emitter;

    @Inject
    PratoRepository pratoRepository;

    @Inject
    RestauranteRepository restauranteRepository;

    @GET
    @Counted(name = "count_get_restaurantes", description = "qtde requisicoes para este endpoint")
    @Timed(name = "time_get_restaurante", description = "qtde de tempo para execucao endpoint get restaurante", unit = MetricUnits.MILLISECONDS)
    public List<Restaurante> getListaRestaurantes() {
        return restauranteRepository.findAll();
    }

    @GET
    @Path("{id}")
    public Restaurante getId(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        return restauranteOp.get();
    }

    @GET
    @Path("/cnpj/{cnpj}/id/{id}")
    public List<Restaurante> getRestCnpj(@PathParam("cnpj") String cnpj, @PathParam("id") String id) {
        System.out.println("cnpj = " + cnpj);
        System.out.println("id = " + id);
        Long idlong = Long.parseLong(id);
        List<Restaurante> lista = restauranteRepository.findByRestauranteCNPJ(cnpj, idlong);
        return lista;
    }

    @POST
    @Transactional
    @Timed(name = "time_post_restaurante", description = "qtde de tempo para execucao endpoint get restaurante", unit = MetricUnits.MILLISECONDS)
    public Response adicionar(@Valid AdicionarRestauranteDto dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restauranteRepository.save(restaurante);

        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(restaurante);
        emitter.send(json);

        return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void alterar(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        if (restauranteOp.isEmpty())
            throw new NotFoundException();
        Restaurante restaurante = restauranteOp.get();
        restaurante.nome = dto.nome;
        restauranteRepository.save(restaurante);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void excluir(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        restauranteOp.ifPresentOrElse(restauranteRepository::delete, () -> {
            throw new NotFoundException();
        });
    }

    @GET
    @Path("/pratos")
    public List<Prato> getListaPratos() {
        return pratoRepository.findAll();
    }

    @GET
    @Path("/{id}/pratos")
    public List<Prato> getListaPratosRestaurante(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        if (!restauranteOp.isPresent()) {
            throw new NotFoundException("Restaurante nao encontrado !!");
        }
        List<Prato> pratos = new ArrayList<>();
        pratos = pratoRepository.findByPratoPeloCodRestaurante(id);

        return pratos;
    }

    @PUT
    @Path("{id}/prato/{id_prato}")
    @Transactional
    public void alterarPrato(@PathParam("id") Long id, @PathParam("id_prato") Long id_prato, AdicionarPratoDto dto) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        if (restauranteOp.isEmpty())
            throw new NotFoundException("Restaurante não encontrado !!");

        Optional<Prato> pratoOp = pratoRepository.findById(id_prato);
        if (pratoOp.isEmpty())
            throw new NotFoundException("Prato não encontrado !!");

        Prato prato = pratoOp.get();

        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;

        pratoRepository.save(prato);
    }

    @DELETE
    @Path("{id}/prato/{id_prato}")
    @Transactional
    public void excluiPrato(@PathParam("id") Long id, @PathParam("id_prato") Long id_prato) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        if (restauranteOp.isEmpty())
            throw new NotFoundException("Restaurante não encontrado !!");

        Optional<Prato> pratoOp = pratoRepository.findById(id_prato);
        if (pratoOp.isEmpty())
            throw new NotFoundException("Prato não encontrado !!");

        Prato prato = pratoOp.get();
        pratoRepository.delete(prato);
    }

    @POST
    @Path("{id}/prato")
    @Transactional
    public Response adicionarPrato(@PathParam("id") Long id, AdicionarPratoDto dto) {
        Optional<Restaurante> restauranteOp = restauranteRepository.findById(id);
        if (restauranteOp.isEmpty())
            throw new NotFoundException("Restaurante inexistente ...");
        Restaurante restaurante = restauranteOp.get();
        Prato prato = pratoMapper.toPrato(dto);
        prato.restaurante = restaurante;
        pratoRepository.save(prato);

        return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

}