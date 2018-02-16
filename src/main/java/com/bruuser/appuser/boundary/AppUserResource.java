package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

    @PUT
    public AppUser save(@NotNull AppUser user) {
        return appUserManager.save(user);
    }

    @DELETE
    @Path("{userName}")
    public void delete(@PathParam("userName") String userName) {
        appUserManager.delete(userName);
    }
}
