SELECT vendor_name, COUNT(*) AS invoice_count,
       SUM(invoice_total) AS invoice_total_sum
FROM vendors v JOIN invoices i
  ON v.vendor_id = i.vendor_id
GROUP BY vendor_name
ORDER BY invoice_count DESC
