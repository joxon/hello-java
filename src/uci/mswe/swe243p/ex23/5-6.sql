update
  invoices
set
  credit_total = 0.1 * invoice_total,
  payment_total = 0.9 * invoice_total
where
  invoice_id = 115;