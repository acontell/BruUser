package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class AppUserResourceTest {

    private static final long APP_USER_ID = 1;
    private static final String MOCK_URI = "test/1";
    private static final AppUser USER = new AppUser();
    private static final int RESPONSE_CREATED_STATUS = 201;
    private AppUserResource cut;

    @Mock
    private AppUserManager appUserManager;
    @Mock
    private UriInfo uriInfo;
    @Mock
    private UriBuilder uriBuilder;

    @Before
    public void setUp() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        cut = new AppUserResource();
        cut.appUserManager = appUserManager;
        when(appUserManager.getById(APP_USER_ID)).thenReturn(USER);
        when(appUserManager.save(USER)).thenReturn(USER);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(new URI(MOCK_URI));
    }

    @Test
    public void getByIdShouldCallAppUserManager() {
        cut.getById(APP_USER_ID);
        verify(appUserManager).getById(APP_USER_ID);
    }

    @Test
    public void getByIdShouldCallReturnAppUserManagerGetByIdResult() {
        assertEquals(cut.getById(APP_USER_ID), USER);
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
    public void deleteShouldCallAppUserManagerDelete() {
        cut.delete(APP_USER_ID);
        verify(appUserManager).delete(APP_USER_ID);
    }

}
