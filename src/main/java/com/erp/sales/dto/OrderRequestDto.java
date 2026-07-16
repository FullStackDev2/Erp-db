package com.erp.sales.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull
    private UUID customerId;

    @NotEmpty
    private List<OrderLineDto> lines;

    @Getter
    @Setter
    public static class OrderLineDto {
        @NotNull
        private String sku;

        @NotNull
        private Integer quantity;
    }
}