package com.tallence.coding.gw.shoes.service;

import com.tallence.coding.gw.shoes.clients.ShoesClient;
import com.tallence.coding.gw.shoes.dto.PageDTO;
import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import com.tallence.coding.gw.shoes.utils.TransformUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class ShoeService {

    @RestClient
    ShoesClient shoesClient;

    public Uni<List<ShoeDTO>> getTransformedShoes() {
        Uni<PageDTO> shoesUni = shoesClient.getListAll();

        return shoesUni.onItem().transformToUni(TransformUtils::transformShoes);
    }
}
