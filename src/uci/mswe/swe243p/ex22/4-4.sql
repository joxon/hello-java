select
  v.vendor_name,
  i.invoice_date,
  i.invoice_number,
  ili.invoice_sequence as li_sequence,
  ili.line_item_amount as li_amount
from
  vendors v,
  invoices i,
  invoice_line_items ili
where
  v.vendor_id = i.vendor_id
  and i.invoice_id = ili.invoice_id
order by
  vendor_name,
  invoice_date,
  invoice_number,
  invoice_sequence;