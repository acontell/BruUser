package com.bruuser.business.validation;

import com.bruuser.business.appuser.entity.AppUser;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class OnlyLettersAndSpacesCheckConstraintValidatorTest {

    private OnlyLettersAndSpacesCheckConstraintValidator cut;

    @Before
    public void setUp() {
        cut = new OnlyLettersAndSpacesCheckConstraintValidator();
    }

    @Test
    public void testIsValid() {
        assertFalse(cut.isValid(new AppUser(), null));
    }
}
