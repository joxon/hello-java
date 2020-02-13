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
      join invoice_line_items
    group by
      account_number
    having
      count_line_items > 1
  ) as table_group_by_account_number
group by
  account_description
order by
  sum_line_item_amount desc;
-- ? work but not grouped by account number
select
  account_description,
  count(*) as count_line_items,
  sum(line_item_amount) as sum_line_item_amount
from
  general_ledger_accounts natural
  join invoice_line_items
group by
  account_description
having
  count_line_items > 1
order by
  sum_line_item_amount desc;