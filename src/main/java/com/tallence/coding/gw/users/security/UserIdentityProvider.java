package com.tallence.coding.gw.users.security;

import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.service.UsersService;
import com.tallence.coding.gw.users.utils.PasswordUtils;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class UserIdentityProvider implements IdentityProvider<UsernamePasswordAuthenticationRequest> {

    @Inject
    private UsersService usersService;

    @Override
    public Class<UsernamePasswordAuthenticationRequest> getRequestType() {
        return UsernamePasswordAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(UsernamePasswordAuthenticationRequest request,
                                              AuthenticationRequestContext authenticationRequestContext) {
        User user = usersService.getUser(request.getUsername());
        System.out.println("Request User " + request.getUsername());
        usersService.inMemoryUsers.values().forEach(u -> System.out.println(u.getUsername()));
        System.out.println("User " + user);
            if (user != null && PasswordUtils.hashPassword(new String(request.getPassword().getPassword())).equals(user.getPassword())) {
                return Uni.createFrom().item(QuarkusSecurityIdentity.builder()
                        .setPrincipal(new QuarkusPrincipal(user.getUsername()))
                        .addCredential(request.getPassword())
                        .setAnonymous(false)
                        .addRole(user.getRole())
                        .build());
            }
            throw new AuthenticationFailedException("password invalid or user not found");
    }
}
