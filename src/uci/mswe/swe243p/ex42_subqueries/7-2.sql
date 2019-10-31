select
  invoice_number,
  invoice_total
from
  invoices
where
  payment_total > (
    select
      avg(payment_total)
    from
      invoices
    where
      payment_total > 0
  )
order by
  invoice_total desc;