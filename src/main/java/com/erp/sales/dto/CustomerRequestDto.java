package com.erp.sales.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String phone;
}