package com.bruuser.business.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PasswordHashTest {

    private static final String PASSWORD = "asdf4fd3DD";
    private static final String OTHER_PASSWORD = "asdf4fd3DDFF55";
    
    // This test comes from the creator of PasswordHash, I've just cleaned it up a bit.
    // https://gist.github.com/jtan189/3804290
    @Test
    public void testCreateHashAndValidate() throws Exception {
        String hash = PasswordHash.createHash(PASSWORD);
        String secondHash = PasswordHash.createHash(PASSWORD);
        assertFalse(hash.equals(secondHash));
        assertFalse(PasswordHash.validatePassword(OTHER_PASSWORD, hash));
        assertTrue(PasswordHash.validatePassword(PASSWORD, hash));
    }
}
