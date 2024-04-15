package com.tallence.coding.gw.shoes.clients;

import com.tallence.coding.gw.shoes.dto.PageDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/")
@RegisterRestClient(configKey = "shoes-api")
public interface ShoesClient {

    @GET
    public Uni<PageDTO> getListAll();
}
