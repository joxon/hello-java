select
  invoice_due_date as 'Due Date',
  invoice_total as 'Invoice Total',
  invoice_total * 0.1 as '10%',
  invoice_total * 1.1 as 'Plus 10%'
from
  invoices
where
  invoice_total >= 500
  and invoice_total <= 1000
order by
  invoice_due_date desc;