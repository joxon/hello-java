SELECT vendor_id, ROUND(AVG(invoice_total), 2) AS average_invoice_amount
FROM invoices
GROUP BY vendor_id
HAVING AVG(invoice_total) > 2000
ORDER BY average_invoice_amount DESC;

SELECT vendor_name, vendor_state, ROUND(AVG(invoice_total), 2) AS average_invoice_amount
FROM vendors JOIN invoices ON vendors.vendor_id = invoices.vendor_id
GROUP BY vendor_name
HAVING AVG(invoice_total) > 2000
ORDER BY average_invoice_amount DESC;