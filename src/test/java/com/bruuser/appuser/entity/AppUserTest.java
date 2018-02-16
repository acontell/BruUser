package com.bruuser.appuser.entity;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppUserTest {

    @Test
    public void getIdShouldReturnId() {
        // Useless test, just for coverage.
        assertEquals(new AppUser().getId(), 0);
    }
}
