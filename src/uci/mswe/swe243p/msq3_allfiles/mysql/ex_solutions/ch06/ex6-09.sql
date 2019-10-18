SELECT vendor_id, invoice_total - payment_total - credit_total AS balance_due,
	   SUM(invoice_total - payment_total - credit_total) OVER() AS total_due,
       SUM(invoice_total - payment_total - credit_total) OVER(PARTITION BY vendor_id
           ORDER BY invoice_total - payment_total - credit_total) AS vendor_due
FROM invoices
WHERE invoice_total - payment_total - credit_total > 0