package com.erp.finance.entity;

import com.erp.common.entity.BaseEntity;
import com.erp.sales.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "invoices", schema = "finance")
public class Invoice extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "invoice_number", nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(nullable = false, length = 20)
    private String status = "ISSUED";

    @PrePersist
    protected void onInvoiceCreate() {
        if (this.issueDate == null) {
            this.issueDate = LocalDateTime.now();
        }
    }
}
