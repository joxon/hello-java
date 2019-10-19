select
  *
from
  (
    select
      v.vendor_name as vendor_name,
      i.invoice_number as invoice_number,
      i.invoice_date as invoice_date,
      i.invoice_total - i.payment_total - i.credit_total as balance_due
    from
      vendors v,
      invoices i
    where
      v.vendor_id = i.vendor_id
  ) sub_table
where
  balance_due <> 0
order by
  vendor_name asc;