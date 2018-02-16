package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("appuser")
@Produces(MediaType.APPLICATION_JSON)
public class AppUserResource {

    @Inject
    AppUserManager userManager;

    @GET
    @Path("find/{id}")
    public AppUser getById(@PathParam("id") long userId) {
        return userManager.getById(userId);
    }

    @POST
    public void save(AppUser user) {
        userManager.save(user);
    }

    @DELETE
    @Path("delete/{id}")
    public void delete(@PathParam("id") long userId) {
        userManager.delete(userId);
    }
}
