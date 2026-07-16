package com.erp.inventory.controller;

import com.erp.inventory.dto.ProductRequestDto;
import com.erp.inventory.dto.ProductResponseDto;
import com.erp.inventory.entity.Product;
import com.erp.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable UUID id) {
        return toResponseDto(productService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        Product product = toEntity(dto);
        return toResponseDto(productService.create(product));
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable UUID id, @Valid @RequestBody ProductRequestDto dto) {
        Product product = toEntity(dto);
        return toResponseDto(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }

    private ProductResponseDto toResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        return dto;
    }

    private Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        return product;
    }
}
