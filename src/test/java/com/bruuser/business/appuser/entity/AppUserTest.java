package com.bruuser.business.appuser.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AppUserTest {

    private static final String USER_NAME = "acontell";
    private static final AppUser WRONG_USER_NAME_USER = new AppUser("as /df", "Arcadio Contell", "1AsDf");
    private static final AppUser WRONG_FULL_NAME_USER = new AppUser("acontell", "1 Arcadio Contell", "1AsDf");
    private static final AppUser WRONG_PASSWORD_USER = new AppUser("acontell", "Arcadio Contell", "asdf");
    private static final String NEW_PASSWORD = "asdf";

    private AppUser cut;

    @Before
    public void setUp() {
        cut = new AppUser("acontell", "Arcadio Contell", "1AsDf");
    }

    @Test
    public void getUserNameShouldReturnUserName() {
        assertEquals(new AppUser(USER_NAME).getUserName(), USER_NAME);
    }

    @Test
    public void getLastUpdateShouldReturnLastUpdate() {
        assertNull(WRONG_USER_NAME_USER.getLastUpdate());
    }
    
    @Test
    public void getPasswordShouldReturnPassword() {
        assertNotNull(WRONG_USER_NAME_USER.getPassword());
    }
    
    // No more get/set tests...
    
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
    public void isValidShouldReturnTrueOnWrongPasswordButAlreadyCalcutatedPasswordToTrue() {
        cut.setPassword(NEW_PASSWORD);
        cut.setHasPasswordBeenEncrypted(true);
        assertTrue(cut.isValid());
    }
    
    @Test
    public void isValidShouldReturnTrueOnValidEntity() {
        assertTrue(cut.isValid());
    }

    @Test
    public void updateCalculatedFieldsShouldUpdateLastDate() {
        assertNull(cut.getLastUpdate());
        cut.updateCalculatedFields();
        assertNotNull(cut.getLastUpdate());
    }

    @Test
    public void updateCalculatedFieldsShouldUpdatePassword() {
        cut.updateCalculatedFields();
        assertNotEquals(NEW_PASSWORD, cut.getPassword());
    }

    @Test
    public void updateCalculatedFieldsShouldSetAlreadyCalcutatedPasswordToTrue() {
        cut.updateCalculatedFields();
        assertTrue(cut.isHasPasswordBeenEncrypted());
    }

    @Test
    public void updateCalculatedFieldsShouldNotUpdatePasswordIfAlreadyCalcutatedPasswordToTrue() {
        cut.setPassword(NEW_PASSWORD);
        cut.setHasPasswordBeenEncrypted(true);
        cut.updateCalculatedFields();
        assertEquals(cut.getPassword(), NEW_PASSWORD);
    }
}
