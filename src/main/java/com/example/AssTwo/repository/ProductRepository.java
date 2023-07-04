package com.example.AssTwo.repository;

import com.example.AssTwo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring recognize repositories by the fact that they extend one of the predefined Repository interfaces

 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
