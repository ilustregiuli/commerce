package com.giulidev.commerce.repositories;

import com.giulidev.commerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {

}
