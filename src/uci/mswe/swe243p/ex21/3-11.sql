select
  *
from
  (
    select
      invoice_number,
      invoice_total,
      payment_total + credit_total as payment_credit_total,
      invoice_total - payment_total - credit_total as balance_due
    from
      invoices
  ) sub_table
where
  balance_due > 50
order by
  balance_due desc
limit
  5;