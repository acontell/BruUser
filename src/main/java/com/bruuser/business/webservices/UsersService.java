package com.bruuser.business.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class UsersService {

    @WebMethod
    public String sayHello(@WebParam(name = "InitialName") String name) {
        return "HELLOOOO " + name;
    }
}
