package com.bruuser.business.webservices;

import com.bruuser.business.appuser.boundary.AppUsersManager;
import com.bruuser.business.appuser.entity.AppUser;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsersServiceTest {

    private static final String USER_NAME = "acontell";
    private static final String PASSWORD = "password";
    private static final List<AppUser> GET_ALL_RESULT = Collections.singletonList(new AppUser());
    
    private UsersService cut;
    
    @Mock
    private AppUsersManager appUsersManager;

    @Before
    public void setUp() {
        initMocks(this);
        cut = new UsersService();
        cut.appUsersManager = appUsersManager;
        when(appUsersManager.isAuthenticatedUser(USER_NAME, PASSWORD)).thenReturn(true);
        when(appUsersManager.getAll()).thenReturn(GET_ALL_RESULT);
    }

    @Test
    public void getAllShouldCallAppUsersManagerIsAuthenticatedUser() {
        cut.getAll(USER_NAME, PASSWORD);
        verify(appUsersManager).isAuthenticatedUser(USER_NAME, PASSWORD);
    }

    @Test
    public void getAllShouldReturnNullWhenIsNotAuthenticatedUser() {
        assertNull(cut.getAll(USER_NAME, null));
    }

    @Test
    public void getAllShouldCallAppUsersManagerGetAllWhenIsAuthenticatedUser() {
        cut.getAll(USER_NAME, PASSWORD);
        verify(appUsersManager).getAll();
    }
    
    @Test
    public void getAllShouldReturnAppUsersManagerGetAllWhenIsAuthenticatedUser() {
        assertEquals(cut.getAll(USER_NAME, PASSWORD), GET_ALL_RESULT);
    }
}
