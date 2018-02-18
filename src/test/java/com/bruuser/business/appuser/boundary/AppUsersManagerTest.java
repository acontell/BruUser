package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import com.bruuser.business.security.PasswordHash;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppUsersManagerTest {

    private static final String APP_USER_NAME = "acontell";
    private static final String NO_STORED_USER_NAME = "acontell2";
    private static final String NEW_PASSWORD = "F";
    private static final AppUser OLD_USER = new AppUser();
    private static final AppUser NEW_USER = new AppUser();
    private static final Set<ConstraintViolation<AppUser>> VIOLATED_CONSTRAINTS = new HashSet<>(Arrays.asList(null, null));
    
    private AppUsersManager cut;
    
    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<AppUser> typedQuery;
    @Mock
    private Validator validator;

    @Before
    public void setUp() {
        initMocks(this);
        cut = new AppUsersManager();
        cut.em = em;
        cut.validator = validator;
        when(validator.validate(OLD_USER)).thenReturn(new HashSet<>());
        when(em.find(AppUser.class, APP_USER_NAME)).thenReturn(OLD_USER);
        when(em.merge(OLD_USER)).thenReturn(OLD_USER);
        when(em.merge(NEW_USER)).thenReturn(NEW_USER);
        when(em.getReference(AppUser.class, APP_USER_NAME)).thenReturn(OLD_USER);
        when(em.createNamedQuery(AppUser.FIND_ALL, AppUser.class)).thenReturn(typedQuery);
    }

    @Test
    public void getByUserNameShouldCallEntityManagerFind() {
        cut.getByUserName(APP_USER_NAME);
        verify(em).find(AppUser.class, APP_USER_NAME);
    }

    @Test
    public void getByUserNameShouldReturnEntityManagerFind() {
        assertEquals(cut.getByUserName(APP_USER_NAME), OLD_USER);
    }

    @Test
    public void updateShouldCallEntityManagerMerge() {
        cut.update(OLD_USER, NEW_USER);
        verify(em).merge(NEW_USER);
    }

    @Test
    public void updateShouldReturnEntityManagerMerge() {
        assertEquals(cut.update(OLD_USER, NEW_USER), NEW_USER);
    }

    @Test
    public void updateShouldUpdateOldEntityProperties() {
        final AppUser newUser = new AppUser("a", "b", null);
        cut.update(new AppUser("d", "e", NEW_PASSWORD), newUser);
        assertTrue(newUser.isHasPasswordBeenEncrypted());
        assertEquals(newUser.getPassword(), NEW_PASSWORD);
    }

    @Test(expected = ConstraintViolationException.class)
    public void updateShouldThrowConstraintExceptionWhenValidatorComplains() {
        when(validator.validate(NEW_USER)).thenReturn(VIOLATED_CONSTRAINTS);
        cut.update(OLD_USER, NEW_USER);
    }

    @Test
    public void mergeShouldCallEntityManagerMerge() {
        cut.merge(NEW_USER);
        verify(em).merge(NEW_USER);
    }

    @Test
    public void mergeShouldReturnEntityManagerMerge() {
        assertEquals(cut.merge(NEW_USER), NEW_USER);
    }

    @Test(expected = ConstraintViolationException.class)
    public void mergeShouldThrowConstraintExceptionWhenValidatorComplains() {
        when(validator.validate(NEW_USER)).thenReturn(VIOLATED_CONSTRAINTS);
        cut.merge(NEW_USER);
    }

    @Test
    public void deleteShouldCallEntityManagerGetReference() {
        cut.delete(APP_USER_NAME);
        verify(em).getReference(AppUser.class, APP_USER_NAME);
    }

    @Test
    public void deleteShouldCallEntityManagerDelete() {
        cut.delete(APP_USER_NAME);
        verify(em).remove(OLD_USER);
    }

    @Test
    public void deleteShouldSwallowEntityNotFoundException() {
        doThrow(new EntityNotFoundException()).when(em).remove(OLD_USER);
        cut.delete(APP_USER_NAME);
    }

    @Test
    public void getAllShouldCallCreatedNameQuery() {
        cut.getAll();
        verify(em).createNamedQuery(AppUser.FIND_ALL, AppUser.class);
    }

    @Test
    public void getAllShouldCallGetResultListOfTypedQuery() {
        cut.getAll();
        verify(typedQuery).getResultList();
    }

    @Test
    public void isAuthenticatedUserShouldReturnFalseOnUserNameNull() {
        assertFalse(cut.isAuthenticatedUser(null, NEW_PASSWORD));
    }

    @Test
    public void isAuthenticatedUserShouldReturnFalseOnPasswordNull() {
        assertFalse(cut.isAuthenticatedUser(APP_USER_NAME, null));
    }

    @Test
    public void isAuthenticatedUserShouldReturnFalseOnUserNotFound() {
        assertFalse(cut.isAuthenticatedUser(NO_STORED_USER_NAME, NEW_PASSWORD));
    }

    @Test
    public void isAuthenticatedUserShouldReturnFalseOnPasswordNotCoincides() {
        assertFalse(cut.isAuthenticatedUser(APP_USER_NAME, NEW_PASSWORD));
    }

    @Test
    public void isAuthenticatedUserShouldReturnTrue() {
        when(em.find(AppUser.class, APP_USER_NAME)).thenReturn(buildUserWithSaltedPassword(NEW_PASSWORD));
        assertTrue(cut.isAuthenticatedUser(APP_USER_NAME, NEW_PASSWORD));
    }
    
    private AppUser buildUserWithSaltedPassword(String password) {
        final AppUser user = new AppUser(null, null, password);
        user.setPassword(PasswordHash.createHash(password));
        return user;
    }
}
