package com.bruuser.business.appuser.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import javax.json.JsonArray;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

public class AppUsersResourceTest {
    private static final String URI = "http://localhost:8080/bruuser/resources/appuser";
    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI(URI);
    
    @Test
    public void getShouldWithMediaTypeJsonShouldBeSuccessfull() {
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void getShouldWithMediaTypeJsonShouldReturnListOfUsers() {
        // Depends on the state of the server
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        JsonArray allUsers = response.readEntity(JsonArray.class);
        assertTrue(allUsers.isEmpty());
    }
    
    // More tests can be added to perform system tests over bruuser
}
