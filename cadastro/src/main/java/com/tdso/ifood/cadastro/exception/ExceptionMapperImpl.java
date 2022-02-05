package com.tdso.ifood.cadastro.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperImpl implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        System.out.println(" ");
        System.out.println("dentro do MapperImpl");
        // if (exception.getMessage().equalsIgnoreCase(userNotFound)) {
        return Response.status(Response.Status.NOT_FOUND).entity(new String(exception.getMessage())).build();
        // } else {

        // return Response.status(Response.Status.BAD_REQUEST).
        // entity(new ErrorMessage(e.getMessage(), false)).build();

    }

}