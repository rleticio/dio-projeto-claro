package com.estudo.pizzaplanet.dto;

import java.math.BigDecimal;

public record UpdateProductVariationDto(
        String sizeName,
        String description,
        BigDecimal price,
        Boolean available
) {
}
