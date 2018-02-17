package com.bruuser.business.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PasswordHashTest {

    // This test comes from the creator of PasswordHash, I've just cleaned up a bit.
    // https://gist.github.com/jtan189/3804290
    @Test
    public void testCreateHash() throws Exception {
        for (int i = 0; i < 100; i++) {
            String password = "" + i;
            String hash = PasswordHash.createHash(password);
            String secondHash = PasswordHash.createHash(password);
            assertFalse(hash.equals(secondHash));
            String wrongPassword = "" + (i + 1);
            assertFalse(PasswordHash.validatePassword(wrongPassword, hash));
            assertTrue(PasswordHash.validatePassword(password, hash));
        }
    }
}
