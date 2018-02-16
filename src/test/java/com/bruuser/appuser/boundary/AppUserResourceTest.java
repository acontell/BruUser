package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import static javax.ws.rs.core.UriBuilder.fromUri;
import javax.ws.rs.core.UriInfo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class AppUserResourceTest {

    private static final String APP_USER_NAME = "acontell";
    private static final String MOCK_URI = "http://localhost:8080/test";
    private static final String NEW_RESOURCE_STRING = MOCK_URI + AppUserResource.PATH_DELIMITER + APP_USER_NAME;
    private static final URI NEW_RESOURCE_URI = fromUri(NEW_RESOURCE_STRING).build();
    private static final AppUser USER = new AppUser(APP_USER_NAME);
    private static final int RESPONSE_CREATED_STATUS = 201;
    private static final List<AppUser> APP_USERS = Arrays.asList(USER);
    private AppUserResource cut;

    @Mock
    private AppUserManager appUserManager;
    @Mock
    private UriInfo uriInfo;

    @Before
    public void setUp() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        cut = new AppUserResource();
        cut.appUserManager = appUserManager;
        when(appUserManager.getByUserName(APP_USER_NAME)).thenReturn(USER);
        when(appUserManager.save(USER)).thenReturn(USER);
        when(appUserManager.getAll()).thenReturn(APP_USERS);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(fromUri(MOCK_URI));
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
        cut.save(USER, uriInfo);
        verify(appUserManager).save(USER);
    }

    @Test
    public void saveShouldReturnCreatedResponse() {
        assertEquals(cut.save(USER, uriInfo).getStatus(), RESPONSE_CREATED_STATUS);
    }

    @Test
    public void saveShouldReturnResponseWithUriToNewResource() {
        assertEquals(cut.save(USER, uriInfo).getLocation(), NEW_RESOURCE_URI);
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
