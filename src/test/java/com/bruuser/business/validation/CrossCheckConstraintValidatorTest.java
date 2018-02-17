package com.bruuser.business.validation;

import com.bruuser.business.appuser.entity.AppUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CrossCheckConstraintValidatorTest {

    private CrossCheckConstraintValidator cut;

    @Before
    public void setUp() {
        cut = new CrossCheckConstraintValidator();
    }

    @Test
    public void isValidShouldReturnFalse() {
        assertFalse(cut.isValid(new AppUser(), null));
    }

    @Test
    public void isValidShouldReturnTrue() {
        assertTrue(cut.isValid(new AppUser("acontell", "Arcadio Contell Monje", "4RcaD10Contell"), null));
    }
}
