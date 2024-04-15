package com.tallence.coding.gw.shoes.utils;

import com.tallence.coding.gw.shoes.dto.PageDTO;
import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import com.tallence.coding.gw.shoes.dto.SupportDTO;
import com.tallence.coding.gw.shoes.utils.TransformUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransformUtilsTest {

    @Test
    void transformShoes() {
        List<ShoeDTO> data = new ArrayList<>();
        data.add(new ShoeDTO(1, "Shoe 1", 1999, "#720EA1", "2602"));
        PageDTO pageDTO = new PageDTO(1, 5, 1, 1, data, new SupportDTO());

        Uni<List<ShoeDTO>> shoeDTOsUni = TransformUtils.transformShoes(pageDTO);
        List<ShoeDTO> shoeDTOs = shoeDTOsUni.await().indefinitely();

        ShoeDTO shoeDTO = shoeDTOs.get(0);
        assertEquals(1, shoeDTO.getId());
        assertEquals("Shoe 1" , shoeDTO.getName());
        assertEquals(1999, shoeDTO.getYear());
        assertEquals("rgb(114, 14, 161)", shoeDTO.getColor());
        assertEquals("2602", shoeDTO.getPantone_value());
    }
}