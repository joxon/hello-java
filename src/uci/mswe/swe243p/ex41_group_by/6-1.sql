select
  vendor_id,
  sum(invoice_total)
from
  invoices
group by
  vendor_id;