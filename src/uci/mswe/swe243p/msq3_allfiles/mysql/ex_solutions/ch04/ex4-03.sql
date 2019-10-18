SELECT vendor_name, default_account_number AS default_account, 
       account_description AS description
FROM vendors v JOIN general_ledger_accounts gl
  ON v.default_account_number = gl.account_number
ORDER BY account_description, vendor_name
