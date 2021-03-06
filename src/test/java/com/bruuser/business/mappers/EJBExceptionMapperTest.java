package com.bruuser.business.mappers;

import static com.bruuser.business.mappers.EJBExceptionMapper.NOT_KNOWN;
import static com.bruuser.business.mappers.EJBExceptionMapper.VIOLATION_CONSTRAINT_EXCEPTION;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EJBExceptionMapperTest {

    private EJBExceptionMapper cut;

    @Mock
    private EJBException ex;

    @Before
    public void setUp() {
        initMocks(this);
        cut = new EJBExceptionMapper();
    }

    @Test
    public void toResponseShouldReturnNotKnownResponseWhenCauseIsNull() {
        assertEquals(cut.toResponse(ex), NOT_KNOWN);
    }

    @Test
    public void toResponseShouldReturnNotKnownResponseWhenCauseIsNotConstraintViolationException() {
        when(ex.getCause()).thenReturn(new EJBException());
        assertEquals(cut.toResponse(ex), NOT_KNOWN);
    }

    @Test
    public void toResponseShouldReturnBadRequestResponseWhenCauseIsConstraintViolationException() {
        when(ex.getCause()).thenReturn(new ConstraintViolationException(null, null));
        assertEquals(cut.toResponse(ex), VIOLATION_CONSTRAINT_EXCEPTION);
    }
}
