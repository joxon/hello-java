SELECT *
FROM vendors JOIN invoices
  ON vendors.vendor_id = invoices.vendor_id
  