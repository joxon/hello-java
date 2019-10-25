select
  vendor_name,
  count(distinct account_number) as count_account
from
  vendors natural
  join invoices natural
  join invoice_line_items
group by
  vendor_id
having
  count_account > 1;