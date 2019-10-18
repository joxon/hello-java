SELECT invoice_number, invoice_total
FROM invoices
WHERE payment_total >
     (SELECT AVG(payment_total)
      FROM invoices
      WHERE payment_total > 0)
ORDER BY invoice_total DESC