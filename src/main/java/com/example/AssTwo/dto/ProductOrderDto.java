package com.example.AssTwo.dto;

import com.example.AssTwo.entity.Order;
import com.example.AssTwo.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductOrderDto {
    private Long id;
    private Product product;
    private Order order;
    private int quantity;
    private BigDecimal price;
    private BigDecimal vat;
}
