package com.erp.finance.service;

import com.erp.finance.entity.Invoice;
import com.erp.finance.repository.InvoiceRepository;
import com.erp.sales.entity.Order;
import com.erp.sales.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private static final String ORDER_STATUS_CONFIRMED = "CONFIRMED";

    private static final String INVOICE_STATUS_ISSUED = "ISSUED";
    private static final String INVOICE_STATUS_PAID = "PAID";
    private static final String INVOICE_STATUS_CANCELLED = "CANCELLED";

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * CONFIRMED durumundaki bir siparişten fatura oluşturur.
     * Sipariş CONFIRMED değilse veya zaten faturalanmışsa hata fırlatır.
     */
    @Transactional
    public Invoice createInvoiceFromOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı: " + orderId));

        if (!ORDER_STATUS_CONFIRMED.equals(order.getStatus())) {
            throw new IllegalStateException(
                    "Sadece CONFIRMED durumundaki siparişler faturalandırılabilir. Mevcut durum: " + order.getStatus());
        }

        if (invoiceRepository.existsByOrderId(orderId)) {
            throw new IllegalStateException("Bu sipariş için zaten bir fatura mevcut: " + orderId);
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setStatus(INVOICE_STATUS_ISSUED);

        return invoiceRepository.save(invoice);
    }

    @Transactional(readOnly = true)
    public Invoice getInvoiceById(UUID id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fatura bulunamadı: " + id));
    }

    @Transactional(readOnly = true)
    public Invoice getInvoiceByOrderId(UUID orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Bu sipariş için fatura bulunamadı: " + orderId));
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    @Transactional
    public Invoice markAsPaid(UUID id) {
        Invoice invoice = getInvoiceById(id);

        if (INVOICE_STATUS_CANCELLED.equals(invoice.getStatus())) {
            throw new IllegalStateException("İptal edilmiş bir fatura ödenmiş olarak işaretlenemez.");
        }

        invoice.setStatus(INVOICE_STATUS_PAID);
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice cancelInvoice(UUID id) {
        Invoice invoice = getInvoiceById(id);

        if (INVOICE_STATUS_PAID.equals(invoice.getStatus())) {
            throw new IllegalStateException("Ödenmiş bir fatura iptal edilemez.");
        }

        invoice.setStatus(INVOICE_STATUS_CANCELLED);
        return invoiceRepository.save(invoice);
    }

    /**
     * Basit fatura numarası üretimi: INV-YYYY-<sıra no>
     * Not: Yüksek eşzamanlılıkta çakışma riskine karşı ileride
     * bir DB sequence'e taşınması önerilir.
     */
    private String generateInvoiceNumber() {
        int year = Year.now().getValue();
        long count = invoiceRepository.count() + 1;
        return String.format("INV-%d-%05d", year, count);
    }
}