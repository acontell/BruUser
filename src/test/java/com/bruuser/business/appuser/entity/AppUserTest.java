package com.bruuser.business.appuser.entity;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppUserTest {

    private static final String USER_NAME = "acontell";

    @Test
    public void getUserNameShouldReturnUserName() {
        assertEquals(new AppUser(USER_NAME).getUserName(), USER_NAME);
    }
}
