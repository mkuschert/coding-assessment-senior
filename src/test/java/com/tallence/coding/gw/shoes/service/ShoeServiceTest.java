package com.tallence.coding.gw.shoes.service;

import com.tallence.coding.gw.shoes.clients.ShoesClient;
import com.tallence.coding.gw.shoes.dto.PageDTO;
import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import com.tallence.coding.gw.shoes.dto.SupportDTO;
import com.tallence.coding.gw.shoes.service.ShoeService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ShoeServiceTest {

    @InjectMock
    @RestClient
    ShoesClient shoesClient;

    @Inject
    ShoeService shoeService;

    @Test
    void getTransformedShoes() {
        List<ShoeDTO> data = new ArrayList<>();
        data.add(new ShoeDTO(1, "Shoe 1", 1999, "#720EA1", "2602"));
        PageDTO pageDTO = new PageDTO(1, 5, 1, 1, data, new SupportDTO());
        Mockito.when(shoesClient.getListAll()).thenReturn(Uni.createFrom().item(pageDTO));

        Uni<List<ShoeDTO>> shoeDTOsUni = this.shoeService.getTransformedShoes();
        List<ShoeDTO> shoeDTOs = shoeDTOsUni.await().indefinitely();

        ShoeDTO shoeDTO = shoeDTOs.get(0);
        assertEquals(1, shoeDTO.getId());
        assertEquals("Shoe 1" , shoeDTO.getName());
        assertEquals(1999, shoeDTO.getYear());
        assertEquals("rgb(114, 14, 161)", shoeDTO.getColor());
        assertEquals("2602", shoeDTO.getPantone_value());
    }
}