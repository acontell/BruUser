package com.bruuser.business.webservices;

import com.bruuser.business.appuser.boundary.AppUsersManager;
import com.bruuser.business.appuser.entity.AppUser;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class UsersService {

    @Inject
    AppUsersManager appUsersManager;
    
    @WebMethod
    public List<AppUser> getAll(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        return appUsersManager.isAuthenticatedUser(userName, password) ? appUsersManager.getAll() : null;
    }
}
