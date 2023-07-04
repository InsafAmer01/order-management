package com.example.AssTwo.repository;


import com.example.AssTwo.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring recognize repositories by the fact that they extend one of the predefined Repository interfaces

 */
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

}
