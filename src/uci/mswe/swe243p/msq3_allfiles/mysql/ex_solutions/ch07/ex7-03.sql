SELECT account_number, account_description
FROM general_ledger_accounts gl
WHERE NOT EXISTS
    (SELECT *
     FROM invoice_line_items
     WHERE account_number = gl.account_number)
ORDER BY account_number
