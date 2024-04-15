package com.tallence.coding.gw.shoes.utils;

import com.tallence.coding.gw.shoes.dto.PageDTO;
import com.tallence.coding.gw.shoes.dto.ShoeDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public class TransformUtils {

    private TransformUtils() {};

    public static Uni<List<ShoeDTO>> transformShoes(PageDTO pageDTO) {

        List<ShoeDTO> shoes = pageDTO.getData();
        shoes.forEach(shoe -> {
            String hexColor = shoe.getColor();
            shoe.setColor(TransformUtils.hexToRgb(hexColor));
        });

        return Uni.createFrom().item(shoes);
    }

    private static String hexToRgb(String hexColor) {
        StringBuffer rgbColor = new StringBuffer("rgb(");
        rgbColor.append(Integer.valueOf(hexColor.substring(1, 3), 16));
        rgbColor.append(", ");
        rgbColor.append(Integer.valueOf(hexColor.substring(3, 5), 16));
        rgbColor.append(", ");
        rgbColor.append(Integer.valueOf(hexColor.substring(5, 7), 16));
        rgbColor.append(")");

        return rgbColor.toString();
    }
}
