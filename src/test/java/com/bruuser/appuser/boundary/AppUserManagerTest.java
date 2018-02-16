package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppUserManagerTest {

    private static final String APP_USER_NAME = "acontell";
    private static final AppUser USER = new AppUser();
    private AppUserManager cut;
    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<AppUser> typedQuery;

    @Before
    public void setUp() {
        initMocks(this);
        cut = new AppUserManager();
        cut.em = em;
        when(em.find(AppUser.class, APP_USER_NAME)).thenReturn(USER);
        when(em.merge(USER)).thenReturn(USER);
        when(em.getReference(AppUser.class, APP_USER_NAME)).thenReturn(USER);
        when(em.createNamedQuery(AppUser.FIND_ALL, AppUser.class)).thenReturn(typedQuery);
    }

    @Test
    public void getByIdShouldCallEntityManagerFind() {
        cut.getByUserName(APP_USER_NAME);
        verify(em).find(AppUser.class, APP_USER_NAME);
    }

    @Test
    public void getByIdShouldReturnEntityManagerFind() {
        assertEquals(cut.getByUserName(APP_USER_NAME), USER);
    }

    @Test
    public void saveShouldCallEntityManagerSave() {
        cut.save(USER);
        verify(em).merge(USER);
    }

    @Test
    public void saveShouldReturnEntityManagerSave() {
        assertEquals(cut.save(USER), USER);
    }

    @Test
    public void deleteShouldCallEntityManagerGetReference() {
        cut.delete(APP_USER_NAME);
        verify(em).getReference(AppUser.class, APP_USER_NAME);
    }

    @Test
    public void deleteShouldCallEntityManagerDelete() {
        cut.delete(APP_USER_NAME);
        verify(em).remove(USER);
    }

    @Test
    public void deleteShouldSwallowEntityNotFoundException() {
        doThrow(new EntityNotFoundException()).when(em).remove(USER);
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
}
