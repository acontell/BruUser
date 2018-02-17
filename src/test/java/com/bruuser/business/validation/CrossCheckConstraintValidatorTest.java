package com.bruuser.business.validation;

import com.bruuser.business.appuser.entity.AppUser;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class CrossCheckConstraintValidatorTest {

    private CrossCheckConstraintValidator cut;

    @Before
    public void setUp() {
        cut = new CrossCheckConstraintValidator();
    }

    @Test
    public void testIsValid() {
        assertFalse(cut.isValid(new AppUser(), null));
    }
}
