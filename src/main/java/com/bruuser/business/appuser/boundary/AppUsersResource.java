package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("appuser")
@Produces(MediaType.APPLICATION_JSON)
public class AppUsersResource {

    @Inject
    AppUsersManager appUsersManager;

    @Path("{userName}")
    public AppUserResource getAppUserResource(@PathParam("userName") String userName) {
        return new AppUserResource(appUsersManager, userName);
    }

    @GET
    public List<AppUser> getAll() {
        return appUsersManager.getAll();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public AppUser save(@Valid AppUser user) {
        return appUsersManager.save(user);
    }
}
