package com.erp.sales.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto {
    private UUID id;
    private UUID customerId;
    private String status;
    private BigDecimal totalAmount;
    private int itemCount;
}