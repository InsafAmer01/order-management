package com.example.AssTwo.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Date;
@Data

public class OrderDto {

    @NotNull
    private Long id;
    private int customerId;
    private Date orderedAt;
}
