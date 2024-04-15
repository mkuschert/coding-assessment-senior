package com.tallence.coding.gw.shoes.routes;

import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import com.tallence.coding.gw.shoes.service.ShoeService;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
@PermitAll
public class ApiRoutes {

    @Inject
    private ShoeService shoeService;

    @Route(path = "/transformed-shoes", methods = Route.HttpMethod.GET)
    public Uni<List<ShoeDTO>> transformedShoes() {
        Uni<List<ShoeDTO>> shoes = shoeService.getTransformedShoes();
        return shoes;
    }
}
