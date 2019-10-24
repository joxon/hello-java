select
  invoice_number,
  invoice_total,
  payment_total + credit_total as payment_credit_total,
  invoice_total - payment_total - credit_total as balance_due
from
  invoices
having
  -- where: before the table is generated
  -- having: after the table is generated
  balance_due > 50
order by
  balance_due desc
limit
  5;