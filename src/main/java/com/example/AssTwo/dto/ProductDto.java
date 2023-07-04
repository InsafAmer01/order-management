package com.example.AssTwo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    @NotNull

    private String slug;

    private String name;

    private String reference;

    private double price;

    private double vat;

    private boolean stockable;

}
