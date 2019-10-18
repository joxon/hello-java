SELECT vendor_name, vendor_city
FROM vendors
WHERE vendor_id = 34;

SELECT COUNT(*) AS number_of_invoices,
    SUM(invoice_total - payment_total - credit_total) AS total_due
FROM invoices
WHERE vendor_id = 34;