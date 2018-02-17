package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

public class AppUserResource {

    private final AppUsersManager appUsersManager;
    private final String userName;

    public AppUserResource(AppUsersManager appUsersManager, String userName) {
        this.appUsersManager = appUsersManager;
        this.userName = userName;
    }

    @GET
    public AppUser getByUserName() {
        return appUsersManager.getByUserName(userName);
    }

    @DELETE
    public void delete() {
        appUsersManager.delete(userName);
    }
}
