package com.estudo.pizzaplanet.repositories;

import com.estudo.pizzaplanet.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//<class, type of id property>
public interface ProductRepository extends JpaRepository<Product, Long> {
}
