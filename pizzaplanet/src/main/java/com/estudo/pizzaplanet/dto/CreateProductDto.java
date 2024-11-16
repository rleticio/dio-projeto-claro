package com.estudo.pizzaplanet.dto;

//The components are shallowly immutable instance variables, which means that they cannot be changed once a record instance has been created. However, mutable objects contained in a record can be changed.
//record components are private and can only be accessed through accessor methods
//createProductDto.name()
//createProductDto.description()
//createProductDto.category()
//createProductDto.productVariations()
//createProductDto.available()
//automatically generate standard implementations of commonly used methods like toString(), hashCode(), and equals()
//You can also create a custom constructor for input validation and field initialization
//public record CreateProductDto(
//...
//) {
//    public CreateProductDto {
//        if (name == null) {
//            throw new IllegalArgumentException("Name cannot be null");
//        }
//    }
//}

//all fields being final and the class being implicitly final as well, meaning it cannot be extended

import java.util.List;

//obs: category is a String instead enum
public record CreateProductDto(
        String name,
        String description,
        String category,
        List<CreateProductVariationDto> productVariations,
        Boolean available
) {
}
