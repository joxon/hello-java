SELECT vendor_id, invoice_total - payment_total - credit_total AS balance_due,
	   SUM(invoice_total - payment_total - credit_total) OVER() AS total_due,
       SUM(invoice_total - payment_total - credit_total) OVER vendor_window AS vendor_due,
       ROUND(AVG(invoice_total - payment_total - credit_total) OVER vendor_window, 2) AS vendor_avg
FROM invoices
WHERE invoice_total - payment_total - credit_total > 0
WINDOW vendor_window AS (PARTITION BY vendor_id ORDER BY invoice_total - payment_total - credit_total)