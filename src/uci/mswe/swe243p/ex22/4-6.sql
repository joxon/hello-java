select
  gla.account_number as account_number,
  gla.account_description as account_description
from
  general_ledger_accounts gla
  left outer join invoice_line_items ili on gla.account_number = ili.account_number
where
  ili.invoice_id is null
order by
  account_number;