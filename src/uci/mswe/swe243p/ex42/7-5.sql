select
  vendor_id,
  max(invoice_total) as max_unpaid
from
  invoices
where
  -- search those who have balance due > 0 (unpaid)
  invoice_total - payment_total - credit_total > 0
group by
  vendor_id;
-- sum of the largest unpaid
select
  sum(max_unpaid)
from
  (
    select
      vendor_id,
      max(invoice_total) as max_unpaid
    from
      invoices
    where
      invoice_total - payment_total - credit_total > 0
    group by
      vendor_id
  ) as max_unpaid_for_each_vendor;