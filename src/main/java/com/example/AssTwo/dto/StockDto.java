package com.example.AssTwo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class StockDto {
    private Long id;
    @NotNull
    
    private int productId;
    
    private int quantity;

    private Date updatedAt;


}
