package com.estudo.pizzaplanet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="product_variations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sizeName;

    private String description;

    private Boolean available;

    private BigDecimal price;

    //ManyToOne This annotation indicates that each ProductVariation is associated with one Product. This is the "many" side of the relationship
    //@JoinColumn(name = "product_id"): This annotation tells Hibernate to use the product_id column in the ProductVariation table as the foreign key to the Product table. This is the "one" side of the relationship.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
