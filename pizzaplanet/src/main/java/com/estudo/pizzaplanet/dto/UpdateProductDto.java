package com.estudo.pizzaplanet.dto;

public record UpdateProductDto(
        String name,
        String description,
        Boolean available
) {
}
