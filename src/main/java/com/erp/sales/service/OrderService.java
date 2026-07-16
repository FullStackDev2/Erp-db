package com.erp.sales.service;

import com.erp.common.exception.ResourceNotFoundException;
import com.erp.inventory.entity.Product;
import com.erp.inventory.repository.ProductRepository;
import com.erp.sales.entity.Customer;
import com.erp.sales.entity.Order;
import com.erp.sales.entity.OrderItem;
import com.erp.sales.repository.CustomerRepository;
import com.erp.sales.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                         CustomerRepository customerRepository,
                         ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    // Basit bir DTO yerine şimdilik sku + quantity çiftlerini parametre olarak alıyoruz
    public record OrderLineRequest(String sku, Integer quantity) {}

    @Transactional
    public Order createOrder(UUID customerId, List<OrderLineRequest> lines) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı: " + customerId));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("PENDING");

        BigDecimal total = BigDecimal.ZERO;

        for (OrderLineRequest line : lines) {
            Product product = productRepository.findBySku(line.sku())
                    .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı, SKU: " + line.sku()));

            if (product.getStockQuantity() < line.quantity()) {
                throw new IllegalArgumentException(
                        "Yetersiz stok. SKU: " + line.sku() + ", mevcut: " + product.getStockQuantity());
            }

            // Stoktan düş
            product.setStockQuantity(product.getStockQuantity() - line.quantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(line.quantity());
            item.setUnitPrice(product.getPrice());
            order.getItems().add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(line.quantity())));
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sipariş bulunamadı: " + id));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}