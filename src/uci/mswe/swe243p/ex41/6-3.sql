select
  vendor_name,
  count(invoice_id) as count_invoice_id,
  sum(invoice_total) as sum_invoice_total
from
  vendors natural
  join invoices
group by
  vendor_id
order by
  count_invoice_id desc;