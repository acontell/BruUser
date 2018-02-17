package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
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
    private AppUserResource cut;

    @Mock
    private AppUsersManager appUserManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cut = new AppUserResource(appUserManager, APP_USER_NAME);
        when(appUserManager.getByUserName(APP_USER_NAME)).thenReturn(USER);
    }

    @Test
    public void getByUserNameShouldCallAppUserManager() {
        cut.getByUserName();
        verify(appUserManager).getByUserName(APP_USER_NAME);
    }

    @Test
    public void getByUserNameShouldCallReturnAppUserManagerGetByIdResult() {
        assertEquals(cut.getByUserName(), USER);
    }

    @Test
    public void deleteShouldCallAppUserManagerDelete() {
        cut.delete();
        verify(appUserManager).delete(APP_USER_NAME);
    }
}
