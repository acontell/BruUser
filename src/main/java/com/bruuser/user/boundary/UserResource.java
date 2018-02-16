package com.bruuser.user.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("ping")
public class UserResource {

    @GET
    public String ping() {
        return "Enjoy Java EE 8!";
    }

}
