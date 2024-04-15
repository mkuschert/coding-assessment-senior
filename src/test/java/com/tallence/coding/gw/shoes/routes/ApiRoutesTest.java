package com.tallence.coding.gw.shoes.routes;

import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import com.tallence.coding.gw.shoes.service.ShoeService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;

@QuarkusTest
class ApiRoutesTest {

    @InjectMock
    ShoeService shoeService;

    @Test
    void transformedShoes() {
        List<ShoeDTO> shoeDTOs = new ArrayList<>();
        shoeDTOs.add(new ShoeDTO(1, "Shoe 1", 1999, "rgb(114, 14, 161)", "2602"));
        Mockito.when(shoeService.getTransformedShoes()).thenReturn(Uni.createFrom().item(shoeDTOs));

        given()
                .when().get("/transformed-shoes").then()
                .statusCode(200)
                .body("$.size()", CoreMatchers.is(1),
                        "id", contains(1), "name", contains("Shoe 1"), "year", contains(1999),
                        "color", contains("rgb(114, 14, 161)"), "pantone_value", contains("2602"));
    }
}