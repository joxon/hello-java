SELECT invoice_total, CONCAT('$', invoice_total)
FROM invoices;

SELECT invoice_number, 989319/invoice_number
FROM invoices;

SELECT invoice_date, invoice_date + 1
FROM invoices;