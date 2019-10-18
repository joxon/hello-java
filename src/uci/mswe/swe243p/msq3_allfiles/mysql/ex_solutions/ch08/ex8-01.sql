SELECT invoice_total,
       FORMAT(invoice_total, 1) AS total_format,
       CONVERT(invoice_total, SIGNED) AS total_convert, 
       CAST(invoice_total AS SIGNED) AS total_cast
FROM invoices
