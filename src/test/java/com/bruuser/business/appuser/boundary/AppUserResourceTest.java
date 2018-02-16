package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class AppUserResourceTest {

    private static final String APP_USER_NAME = "acontell";
    private static final AppUser USER = new AppUser(APP_USER_NAME);
    private static final List<AppUser> APP_USERS = Arrays.asList(USER);
    private AppUserResource cut;

    @Mock
    private AppUserManager appUserManager;

    @Before
    public void setUp() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        cut = new AppUserResource();
        cut.appUserManager = appUserManager;
        when(appUserManager.getByUserName(APP_USER_NAME)).thenReturn(USER);
        when(appUserManager.save(USER)).thenReturn(USER);
        when(appUserManager.getAll()).thenReturn(APP_USERS);
    }

    @Test
    public void getByIdShouldCallAppUserManager() {
        cut.getByUserName(APP_USER_NAME);
        verify(appUserManager).getByUserName(APP_USER_NAME);
    }

    @Test
    public void getByIdShouldCallReturnAppUserManagerGetByIdResult() {
        assertEquals(cut.getByUserName(APP_USER_NAME), USER);
    }

    @Test
    public void saveShouldCallAppUserManagerSave() {
        cut.save(USER);
        verify(appUserManager).save(USER);
    }

    @Test
    public void deleteShouldCallAppUserManagerDelete() {
        cut.delete(APP_USER_NAME);
        verify(appUserManager).delete(APP_USER_NAME);
    }

    @Test
    public void getAllShouldCallAppUserManagerGetAll() {
        cut.getAll();
        verify(appUserManager).getAll();
    }

    @Test
    public void getAllShouldReturnAppUserManagerGetAll() {
        assertEquals(cut.getAll(), APP_USERS);
    }
}
