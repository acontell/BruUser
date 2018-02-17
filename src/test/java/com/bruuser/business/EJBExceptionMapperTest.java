package com.bruuser.business;

import static com.bruuser.business.EJBExceptionMapper.NOT_KNOWN;
import static com.bruuser.business.EJBExceptionMapper.OPTIMISTIC_LOCKING_EXCEPTION;
import static com.bruuser.business.EJBExceptionMapper.VIOLATION_CONSTRAINT_EXCEPTION;
import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
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
    public void toResponseShouldReturnNotKnownResponseWhenCauseIsNotOptimisticLockingOrConstraintViolationException() {
        when(ex.getCause()).thenReturn(new EJBException());
        assertEquals(cut.toResponse(ex), NOT_KNOWN);
    }

    @Test
    public void toResponseShouldReturnOptimisticResponseWhenCauseIsOptimisticLocking() {
        when(ex.getCause()).thenReturn(new OptimisticLockException());
        assertEquals(cut.toResponse(ex), OPTIMISTIC_LOCKING_EXCEPTION);
    }
    
    @Test
    public void toResponseShouldReturnBadRequestResponseWhenCauseIsConstraintViolationException() {
        when(ex.getCause()).thenReturn(new ConstraintViolationException(null, null));
        assertEquals(cut.toResponse(ex), VIOLATION_CONSTRAINT_EXCEPTION);
    }
}
