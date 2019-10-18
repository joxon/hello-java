SELECT invoice_number, invoice_total - payment_total - credit_total AS balance_due,
	   RANK() OVER(ORDER BY invoice_total - payment_total - credit_total DESC) AS balance_rank
FROM invoices
WHERE invoice_total - payment_total - credit_total > 0