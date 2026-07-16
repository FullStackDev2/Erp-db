package com.erp.inventory;

import com.erp.inventory.entity.Product;
import com.erp.inventory.repository.ProductRepository;
import com.erp.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findById_shouldReturnProduct_whenExists() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setSku("TEST-001");
        product.setName("Test Ürün");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(5);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.findById(id);

        assertThat(result.getSku()).isEqualTo("TEST-001");
    }
}
