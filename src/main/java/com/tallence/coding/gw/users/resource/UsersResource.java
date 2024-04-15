package com.tallence.coding.gw.users.resource;

import com.tallence.coding.gw.users.dto.UpdatePasswordDTO;
import com.tallence.coding.gw.users.dto.UpdateUserDTO;
import com.tallence.coding.gw.users.mapper.UserMapper;
import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.service.UsersService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("users")
@RolesAllowed("admin")
public class UsersResource {

    @Inject
    UsersService service;

    @GET
    @Path("{username}")
    public Uni<Response> getByUsername(String username) {
        return service.getByUsername(username).onItem()
                .transform(user -> user != null ? Response.ok(UserMapper.modelToUserDTO(user)) : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @POST
    public Uni<Response> createUser(User user) {
        return service.createUser(user).onItem()
                .transform(username -> username != null ? Response.created(URI.create("/users/" + username)) : Response.status(Response.Status.BAD_REQUEST))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @PUT
    @Path("{username}")
    public Uni<Response> updateUser(String username, UpdateUserDTO updateUserDTO) {
        return service.updateUser(username, updateUserDTO)
                .onItem().transform(updated -> updated ? Response.Status.OK : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @PUT
    @Path("{username}/password")
    public Uni<Response> updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        return service.updatePassword(username, updatePasswordDTO)
                .onItem().transform(updated -> updated ? Response.Status.OK : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{username}")
    public Uni<Response> deleteUser(String username) {
        return service.deleteUser(username)
                .onItem().transform(deleted -> deleted ? Response.Status.OK : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}
