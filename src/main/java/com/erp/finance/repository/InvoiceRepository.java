package com.erp.finance.repository;

import com.erp.finance.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Optional<Invoice> findByOrderId(UUID orderId);

    List<Invoice> findByStatus(String status);

    boolean existsByOrderId(UUID orderId);
}
