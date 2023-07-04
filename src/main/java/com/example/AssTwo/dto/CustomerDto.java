package com.example.AssTwo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class CustomerDto {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
