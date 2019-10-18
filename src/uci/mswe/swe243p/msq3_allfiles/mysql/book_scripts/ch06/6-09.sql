SELECT vendor_id, invoice_date, invoice_total,
       SUM(invoice_total) OVER() AS total_invoices,
       SUM(invoice_total) OVER(PARTITION BY vendor_id) AS vendor_total
FROM invoices
WHERE invoice_total > 5000;

SELECT vendor_id, invoice_date, invoice_total,
       SUM(invoice_total) OVER() AS total_invoices,
       SUM(invoice_total) OVER(PARTITION BY vendor_id ORDER BY invoice_total) AS vendor_total
FROM invoices
WHERE invoice_total > 5000;