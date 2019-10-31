select
  invoice_number,
  invoice_date,
  invoice_total - payment_total - credit_total as balance_due,
  payment_date
from
  invoices
where
  payment_date is null;