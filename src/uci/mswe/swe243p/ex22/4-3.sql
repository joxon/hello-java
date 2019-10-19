select
  v.vendor_name as vendor_name,
  v.default_account_number as default_account,
  gla.account_description as 'description'
from
  vendors v,
  general_ledger_accounts gla
where
  v.default_account_number = gla.account_number
order by
  account_description,
  vendor_name;