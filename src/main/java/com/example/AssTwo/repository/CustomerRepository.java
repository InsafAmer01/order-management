package com.example.AssTwo.repository;


import com.example.AssTwo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring recognize repositories by the fact that they extend one of the predefined Repository interfaces

 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
