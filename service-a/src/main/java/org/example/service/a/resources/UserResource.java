package org.example.service.a.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.services.UserService;

@LoggingInterceptor
@Path("/user")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public Response getUser(@PathParam("email") String email) {
        try {
            var user = userService.getUser(email);
            return Response.status(Response.Status.OK)
                    .entity(user)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
