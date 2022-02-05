package com.tdso.ifood.cadastro.rest;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tdso.ifood.cadastro.model.dto.LivroDto;
import com.tdso.ifood.cadastro.service.LivroService;

@Path("/livros")
public class LivroResource {

    @Inject
    Validator validator;

    @Inject
    LivroService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Produces(MediaType.TEXT_XML)
    public String incluiLivrosValidationManual(LivroDto livroDto) {

        Set<ConstraintViolation<LivroDto>> violations = validator.validate(livroDto);
        if (violations.isEmpty())
            return new String("Livro Validado");

        return violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(", "));

    }

    @Path("/auto")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Produces(MediaType.APPLICATION_JSON)
    public String incluiLivrosValidationAuto(@Valid LivroDto livroDto) {

        return new String("Livro Validado");
    }

    @Path("/auto/service")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Produces(MediaType.TEXT_XML)
    public Response incluiLivrosValidationAutoService(LivroDto livroDto) {

        try {
            service.ValidaLivro(livroDto);
        } catch (ConstraintViolationException e) {
            String msg = e.getConstraintViolations().stream().map(cv -> cv.getMessage())
                    .collect(Collectors.joining(","));
            return Response.status(Response.Status.BAD_REQUEST).entity(new String(msg)).build();
        }
        return Response.status(Response.Status.OK).build();

    }

    // no exemplo abaixo o id será validado de acordo com a restrição grupo Put,
    // na qual ele nao pode ser nulo
    @Path("/auto/custom")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Produces(MediaType.TEXT_XML)
    public String incluiLivrosValidationAutoCustom(LivroDto livroDto) {

        try {
            service.ValidaLivroCustomGroup(livroDto);
        } catch (ConstraintViolationException e) {
            return e.getConstraintViolations().stream().map(cv -> cv.getMessage()).collect(Collectors.joining(","));
        }
        return new String("Livro Validado");

    }

    //
}