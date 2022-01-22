package com.tdso.ifood.cadastro.rest;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import javax.ws.rs.core.Response.Status;

import com.tdso.ifood.cadastro.model.Prato;

//import com.tdso.ifood.cadastro.model.Prato;
@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

    @GET
    public List<Prato> listaPratos() {
        return Prato.listAll();
    }

    @POST
    @Transactional
    public Response criarPrato(Prato pratoDto) {
        pratoDto.persist();
        return Response.ok("Prato criado !!").build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Response atualizarPrato(@PathParam("id") Long id, Prato pratoDto) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty())
            throw new NotFoundException();
        Prato prato = pratoOp.get();
        prato.nome = pratoDto.nome;
        prato.preco = pratoDto.preco;
        prato.persist();
        return Response.status(Status.OK).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deletePrato(@PathParam("id") Long id) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty())
            throw new NotFoundException();
        Prato.deleteById(id);
        return Response.status(Status.OK).build();
    }

}