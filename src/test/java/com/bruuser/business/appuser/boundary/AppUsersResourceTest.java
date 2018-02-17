package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class AppUsersResourceTest {

    private static final String APP_USER_NAME = "acontell";
    private static final AppUser USER = new AppUser(APP_USER_NAME);
    private static final List<AppUser> APP_USERS = Arrays.asList(USER);
    private AppUsersResource cut;

    @Mock
    private AppUsersManager appUserManager;

    @Before
    public void setUp() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        cut = new AppUsersResource();
        cut.appUsersManager = appUserManager;
        when(appUserManager.save(USER)).thenReturn(USER);
        when(appUserManager.getAll()).thenReturn(APP_USERS);
    }

    @Test
    public void getAppUserResourceShouldReturnNewAppUserResource() {
        assertThat(cut.getAppUserResource(APP_USER_NAME), instanceOf(AppUserResource.class));
    }

    @Test
    public void saveShouldCallAppUserManagerSave() {
        cut.save(USER);
        verify(appUserManager).save(USER);
    }

    @Test
    public void saveShouldReturnAppUserManagerSave() {
        assertEquals(cut.save(USER), USER);
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
