package com.erp.sales.controller;

import com.erp.sales.dto.OrderRequestDto;
import com.erp.sales.dto.OrderResponseDto;
import com.erp.sales.entity.Order;
import com.erp.sales.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponseDto> getAll() {
        return orderService.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable UUID id) {
        return toResponseDto(orderService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto dto) {
        List<OrderService.OrderLineRequest> lines = dto.getLines().stream()
                .map(l -> new OrderService.OrderLineRequest(l.getSku(), l.getQuantity()))
                .collect(Collectors.toList());

        Order order = orderService.createOrder(dto.getCustomerId(), lines);
        return toResponseDto(order);
    }

      @PostMapping("/{id}/confirm")
    public OrderResponseDto confirm(@PathVariable UUID id) {
        return toResponseDto(orderService.confirmOrder(id));
    }

    @PostMapping("/{id}/cancel")
    public OrderResponseDto cancel(@PathVariable UUID id) {
        return toResponseDto(orderService.cancelOrder(id));
    }

    private OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItemCount(order.getItems().size());
        return dto;
    }
}