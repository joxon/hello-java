select
  vendor_id,
  max(invoice_total - payment_total - credit_total) as max_unpaid
from
  invoices
group by
  vendor_id
having
  -- search those who have balance due > 0 (unpaid)
  max_unpaid > 0;
-- sum of the largest unpaid
select
  sum(max_unpaid)
from
  (
    select
      vendor_id,
      max(invoice_total - payment_total - credit_total) as max_unpaid
    from
      invoices
    group by
      vendor_id
    having
      max_unpaid > 0
  ) as max_unpaid_for_each_vendor;