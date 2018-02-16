package com.bruuser.business;

import java.util.Optional;
import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    private static final String CAUSE_HEADER = "cause";
    static final Response NOT_KNOWN = buildResponse(INTERNAL_SERVER_ERROR, "NOT KNOWN");
    static final Response OPTIMISTIC_LOCKING_EXCEPTION = buildResponse(CONFLICT, "CONFLICT");

    private static Response buildResponse(Status status, String causeMsg) {
        return Response.status(status)
                .header(CAUSE_HEADER, causeMsg)
                .build();
    }

    @Override
    public Response toResponse(EJBException ex) {
        return Optional.ofNullable(ex.getCause())
                .map(EJBExceptionMapper::isOptimisticLockException)
                .map(EJBExceptionMapper::getResponse)
                .orElse(NOT_KNOWN);
    }

    private static boolean isOptimisticLockException(Throwable ex) {
        return ex instanceof OptimisticLockException;
    }

    private static Response getResponse(boolean isOptimisticLockException) {
        return isOptimisticLockException
                ? OPTIMISTIC_LOCKING_EXCEPTION
                : NOT_KNOWN;
    }
}
