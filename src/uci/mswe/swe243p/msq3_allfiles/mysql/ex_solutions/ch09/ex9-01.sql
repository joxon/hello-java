SELECT invoice_total, ROUND(invoice_total, 1) AS one_digit, 
    ROUND(invoice_total, 0) AS zero_digits_round,
    TRUNCATE(invoice_total, 0) AS zero_digits_truncate
FROM invoices