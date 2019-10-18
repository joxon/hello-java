CREATE OR REPLACE VIEW open_items
AS
SELECT vendor_name, invoice_number, invoice_total,
  invoice_total - payment_total - credit_total AS balance_due
FROM  vendors JOIN invoices
  ON vendors.vendor_id = invoices.vendor_id
WHERE invoice_total - payment_total - credit_total > 0
ORDER BY vendor_name