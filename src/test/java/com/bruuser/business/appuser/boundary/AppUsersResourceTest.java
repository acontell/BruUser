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
import static org.mockito.MockitoAnnotations.initMocks;

public class AppUsersResourceTest {

    private static final String APP_USER_NAME = "acontell";
    private static final String NEW_APP_USER_NAME = "acontell2";
    private static final AppUser OLD_USER = new AppUser(APP_USER_NAME);
    private static final AppUser NEW_USER = new AppUser(NEW_APP_USER_NAME);
    private static final List<AppUser> APP_USERS = Arrays.asList(OLD_USER);
    
    private AppUsersResource cut;

    @Mock
    private AppUsersManager appUserManager;

    @Before
    public void setUp() throws URISyntaxException {
        initMocks(this);
        cut = new AppUsersResource();
        cut.appUsersManager = appUserManager;
        when(appUserManager.update(OLD_USER, OLD_USER)).thenReturn(OLD_USER);
        when(appUserManager.merge(NEW_USER)).thenReturn(NEW_USER);
        when(appUserManager.getByUserName(APP_USER_NAME)).thenReturn(OLD_USER);
        when(appUserManager.getByUserName(NEW_APP_USER_NAME)).thenReturn(null);
        when(appUserManager.getAll()).thenReturn(APP_USERS);
    }

    @Test
    public void getAppUserResourceShouldReturnNewAppUserResource() {
        assertThat(cut.getAppUserResource(APP_USER_NAME), instanceOf(AppUserResource.class));
    }
    
    @Test
    public void saveShouldCallGetByUserName() {
        cut.save(OLD_USER);
        verify(appUserManager).getByUserName(APP_USER_NAME);
    }

    @Test
    public void saveShouldCallAppUserManagerMergeOnCreation() {
        cut.save(NEW_USER);
        verify(appUserManager).merge(NEW_USER);
    }

    @Test
    public void saveShouldReturnAppUserManagerMergeOnCreation() {
        cut.save(NEW_USER);
        assertEquals(appUserManager.merge(NEW_USER), NEW_USER);
    }
    
    @Test
    public void saveShouldCallUpdateWhenIsNotCreation() {
        cut.save(OLD_USER);
        verify(appUserManager).update(OLD_USER, OLD_USER);
    }

    @Test
    public void saveShouldReturnUpdateWhenIsNotCreation() {
        assertEquals(appUserManager.update(OLD_USER, OLD_USER), OLD_USER);
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
