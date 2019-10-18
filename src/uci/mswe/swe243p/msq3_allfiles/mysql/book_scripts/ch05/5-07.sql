DELETE FROM general_ledger_accounts
WHERE account_number = 306;

DELETE FROM invoice_line_items
WHERE invoice_id = 78 AND invoice_sequence = 2;

DELETE FROM invoice_line_items
WHERE invoice_id = 12;

DELETE FROM invoice_line_items
WHERE invoice_id IN
    (SELECT invoice_id
     FROM invoices
     WHERE vendor_id = 115);