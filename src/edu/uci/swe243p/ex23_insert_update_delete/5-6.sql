-- Can be better?
-- How to reference max(invoice_id) in a WHERE clause?
update
  (invoices as i)
  inner join (
    select
      max(invoice_id) as invoice_id
    from
      invoices
  ) as m on i.invoice_id = m.invoice_id
set
  credit_total = 0.1 * invoice_total,
  payment_total = 0.9 * invoice_total;