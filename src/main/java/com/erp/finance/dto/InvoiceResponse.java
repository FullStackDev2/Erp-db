package com.erp.finance.dto;

import com.erp.finance.entity.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class InvoiceResponse {

    private UUID id;
    private UUID orderId;
    private String invoiceNumber;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private BigDecimal totalAmount;
    private String status;

    public static InvoiceResponse fromEntity(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setOrderId(invoice.getOrder().getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setIssueDate(invoice.getIssueDate());
        response.setDueDate(invoice.getDueDate());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setStatus(invoice.getStatus());
        return response;
    }
}
