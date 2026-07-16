package com.erp.inventory.service;

import com.erp.common.exception.ResourceNotFoundException;
import com.erp.inventory.entity.Product;
import com.erp.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı: " + id));
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı, SKU: " + sku));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(UUID id, Product updatedProduct) {
        Product existing = findById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(existing);
    }

    public void delete(UUID id) {
        productRepository.delete(findById(id));
    }
}
