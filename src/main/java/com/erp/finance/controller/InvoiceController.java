package com.erp.finance.controller;

import com.erp.finance.dto.InvoiceResponse;
import com.erp.finance.entity.Invoice;
import com.erp.finance.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/from-order/{orderId}")
    public ResponseEntity<InvoiceResponse> createFromOrder(@PathVariable UUID orderId) {
        Invoice invoice = invoiceService.createInvoiceFromOrder(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(InvoiceResponse.fromEntity(invoice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getById(@PathVariable UUID id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(InvoiceResponse.fromEntity(invoice));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<InvoiceResponse> getByOrderId(@PathVariable UUID orderId) {
        Invoice invoice = invoiceService.getInvoiceByOrderId(orderId);
        return ResponseEntity.ok(InvoiceResponse.fromEntity(invoice));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAll(
            @RequestParam(required = false) String status) {

        List<Invoice> invoices = (status != null)
                ? invoiceService.getInvoicesByStatus(status)
                : invoiceService.getAllInvoices();

        List<InvoiceResponse> response = invoices.stream()
                .map(InvoiceResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<InvoiceResponse> markAsPaid(@PathVariable UUID id) {
        Invoice invoice = invoiceService.markAsPaid(id);
        return ResponseEntity.ok(InvoiceResponse.fromEntity(invoice));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<InvoiceResponse> cancel(@PathVariable UUID id) {
        Invoice invoice = invoiceService.cancelInvoice(id);
        return ResponseEntity.ok(InvoiceResponse.fromEntity(invoice));
    }
}
