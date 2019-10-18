SELECT invoice_id, invoice_date, invoice_total,
    CAST(invoice_date AS CHAR(10)) AS char_date,
    CAST(invoice_total AS SIGNED) AS integer_total
FROM invoices;

SELECT invoice_id, invoice_date, invoice_total,
    CONVERT(invoice_date, CHAR(10)) AS char_date,
    CONVERT(invoice_total, SIGNED) AS integer_total
FROM invoices;