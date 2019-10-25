select
  vendor_name,
  invoice_id,
  invoice_sequence,
  line_item_amount
from
  vendors natural
  join invoices natural
  join invoice_line_items
where
  invoice_id in (
    select
      invoice_id
    from
      invoice_line_items
    where
      invoice_sequence > 1
  )
order by
  vendor_name,
  invoice_id,
  invoice_sequence;