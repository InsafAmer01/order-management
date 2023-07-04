package com.example.AssTwo.repository;


import com.example.AssTwo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring recognize repositories by the fact that they extend one of the predefined Repository interfaces

 */
public interface StockRepository extends JpaRepository<Stock, Integer> {


}
