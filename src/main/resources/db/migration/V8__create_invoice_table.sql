-- V6: Finance modülü - Invoice tablosu
CREATE SCHEMA IF NOT EXISTS finance;

CREATE TABLE finance.invoices (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id        UUID NOT NULL UNIQUE REFERENCES sales.orders(id),
    invoice_number  VARCHAR(50) NOT NULL UNIQUE,
    issue_date      TIMESTAMP NOT NULL DEFAULT now(),
    due_date        TIMESTAMP,
    total_amount    NUMERIC(12, 2) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'ISSUED',
    created_at      TIMESTAMP NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_invoices_order_id ON finance.invoices(order_id);
CREATE INDEX idx_invoices_status ON finance.invoices(status);