package com.bruuser.business.appuser.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AppUserTest {

    private static final String USER_NAME = "acontell";
    private static final AppUser VALID_USER = new AppUser("acontell", "Arcadio Contell", "1AsDf");
    private static final AppUser WRONG_USER_NAME_USER = new AppUser("as /df", "Arcadio Contell", "1AsDf");
    private static final AppUser WRONG_FULL_NAME_USER = new AppUser("acontell", "1 Arcadio Contell", "1AsDf");
    private static final AppUser WRONG_PASSWORD_USER = new AppUser("acontell", "Arcadio Contell", "asdf");
    private static final String NEW_PASSWORD = "asdf";
    private static final AppUser CHANGE_REQUEST = new AppUser("acontell", "Arcadio Contell", NEW_PASSWORD);

    @Test
    public void getUserNameShouldReturnUserName() {
        assertEquals(new AppUser(USER_NAME).getUserName(), USER_NAME);
    }

    @Test
    public void isValidShouldReturnFalseOnWrongUserName() {
        assertFalse(WRONG_USER_NAME_USER.isValid());
    }

    @Test
    public void isValidShouldReturnFalseOnWrongFullName() {
        assertFalse(WRONG_FULL_NAME_USER.isValid());
    }

    @Test
    public void isValidShouldReturnFalseOnWrongPassword() {
        assertFalse(WRONG_PASSWORD_USER.isValid());
    }

    @Test
    public void isValidShouldReturnTrueOnValidEntity() {
        assertTrue(VALID_USER.isValid());
    }

    @Test
    public void updateCalculatedFieldsShouldUpdateLastDate() {
        assertNull(CHANGE_REQUEST.getLastUpdate());
        CHANGE_REQUEST.updateCalculatedFields();
        assertNotNull(CHANGE_REQUEST.getLastUpdate());
    }

    @Test
    public void updateCalculatedFieldsShouldUpdatePassword() {
        CHANGE_REQUEST.updateCalculatedFields();
        assertNotEquals(NEW_PASSWORD, CHANGE_REQUEST.getPassword());
    }
}
