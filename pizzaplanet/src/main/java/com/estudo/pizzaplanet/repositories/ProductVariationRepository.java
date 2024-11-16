package com.estudo.pizzaplanet.repositories;


import com.estudo.pizzaplanet.entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductVariationRepository extends JpaRepository<ProductVariation,Long> {

    // pv.product refers to the Product entity associated with the ProductVariation pv, and pv.product.id refers to the id field of the Product entity

    // In JPA and Spring Data JPA, it's more common to use the property name of the association, not the column name. So, instead of pv.product_id, you would typically use pv.product.id. This is because pv.product refers to the Product entity associated with the ProductVariation, and pv.product.id refers to the id field of the Product entity.
    // The reason for this is that JPA operates on Java objects, not on database tables and columns. So, when you write a JPQL (Java Persistence Query Language) query, you should refer to the properties of your Java entities, not the columns of your database tables.
    @Query("select pv from ProductVariation pv where pv.product.id = :productId and pv.id = :productVariationId")
    Optional<ProductVariation> findByProductIdAndProductVariationId(@Param("productId") Long productId, @Param("productVariationId") Long productVariationId);

}

