package com.bruuser.business.mappers;

import java.util.Optional;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    private static final String CAUSE_HEADER = "cause";
    static final Response NOT_KNOWN = buildResponse(INTERNAL_SERVER_ERROR, "NOT KNOWN");
    static final Response VIOLATION_CONSTRAINT_EXCEPTION = buildResponse(BAD_REQUEST, "BAD REQUEST");

    private static Response buildResponse(Status status, String causeMsg) {
        return Response.status(status)
                .header(CAUSE_HEADER, causeMsg)
                .build();
    }

    @Override
    public Response toResponse(EJBException ex) {
        return Optional.ofNullable(ex.getCause())
                .map(EJBExceptionMapper::isConstraintViolationException)
                .map(EJBExceptionMapper::getResponse)
                .orElse(NOT_KNOWN);
    }

    private static boolean isConstraintViolationException(Throwable ex) {
        return ex instanceof ConstraintViolationException;
    }

    private static Response getResponse(boolean isConstraintViolationException) {
        return isConstraintViolationException
                ? VIOLATION_CONSTRAINT_EXCEPTION
                : NOT_KNOWN;
    }
}
