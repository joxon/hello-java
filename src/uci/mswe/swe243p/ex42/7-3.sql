select
  account_number,
  account_description
from
  general_ledger_accounts as gla
where
  not exists (
    select
      1
    from
      invoice_line_items as ili
    where
      gla.account_number = ili.account_number
  )
order by
  account_number;