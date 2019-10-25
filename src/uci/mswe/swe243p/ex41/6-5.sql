select
  *
from
  (
    select
      account_description,
      count(*) as count_line_items,
      sum(line_item_amount) as sum_line_item_amount
    from
      general_ledger_accounts natural
      join invoice_line_items natural
      join invoices
    where
      invoice_date between '2018-04-01'
      and '2018-06-30'
    group by
      account_number
    having
      count_line_items > 1
  ) as table_group_by_account_number
group by
  account_description
order by
  sum_line_item_amount desc;