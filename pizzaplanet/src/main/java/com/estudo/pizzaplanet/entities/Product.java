package com.estudo.pizzaplanet.entities;


import com.estudo.pizzaplanet.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@Builder - permite instanciar a classe usando o método build();
//Product product = Product.builder()
//      .name(valor)
//      .description(valor)
//      .category(valor)
//      .productVariations(valor)
//      .available(valor)
//      .build();
@Entity
@Table(name="products") //classe no singular e a tabela no plural
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT") //The maximum length of varchar is 255. MySql is "LONGTEXT". TEXT is used to store large amounts of text data.
    private String description;

    @Enumerated(EnumType.STRING) //When you retrieve the Product from the database, JPA will automatically convert the string "PIZZA" back to the Category.PIZZA enum constant
    private Category category;

    //@OneToMany defines a unidirectional one-to-many relationship between the Product and ProductVariation entities. ("First world One" means the current class = Product)
    //mappedBy = "product" tells Hibernate that the ProductVariation entity has a product field that refers back to the Product entity
    //cascade = CascadeType.ALL tells Hibernate to propagate all operations (save, update, delete, etc.) from the Product entity to the ProductVariation entities. For example, if you save a Product entity, all of its ProductVariation entities will also be saved
    //orphanRemoval = true tells Hibernate to remove any ProductVariation entities that are no longer associated with a Product entity. For example, if you remove a ProductVariation entity from a Product's list of ProductVariation entities and then save the Product, the removed ProductVariation entity will also be deleted from the database
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariation> productVariations;

    private Boolean available;
}
