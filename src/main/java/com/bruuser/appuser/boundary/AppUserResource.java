package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("appuser")
@Produces(MediaType.APPLICATION_JSON)
public class AppUserResource {

    static final String PATH_DELIMITER = "/";

    @Inject
    AppUserManager appUserManager;

    @GET
    @Path("{userName}")
    public AppUser getByUserName(@PathParam("userName") String userName) {
        return appUserManager.getByUserName(userName);
    }

    @GET
    @Path("{all}")
    public List<AppUser> getAll() {
        return appUserManager.getAll();
    }
    
    @POST
    public Response save(@NotNull AppUser user, @Context UriInfo uriInfo) {
        URI uri = getUriOfAppUser(uriInfo, appUserManager.save(user));
        return Response.created(uri)
                .build();
    }

    private URI getUriOfAppUser(UriInfo info, AppUser appUser) {
        return info.getAbsolutePathBuilder()
                .path(PATH_DELIMITER + appUser.getUserName())
                .build();
    }

    @DELETE
    @Path("{userName}")
    public void delete(@PathParam("userName") String userName) {
        appUserManager.delete(userName);
    }
}
