CREATE OR REPLACE VIEW open_items_summary
AS
SELECT vendor_name, COUNT(*) AS open_item_count,
       SUM(invoice_total - credit_total - payment_total) AS open_item_total
FROM vendors JOIN invoices
  ON vendors.vendor_id = invoices.vendor_id
WHERE invoice_total - credit_total - payment_total > 0
GROUP BY vendor_name
ORDER BY open_item_total DESC
