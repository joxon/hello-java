-- create the user jane that was deleted in 18-04
CREATE USER IF NOT EXISTS jane IDENTIFIED BY 'sesame';

CREATE ROLE invoice_entry;

GRANT INSERT, UPDATE
ON invoices
TO invoice_entry;

GRANT INSERT, UPDATE
ON invoice_line_items
TO invoice_entry;

GRANT invoice_entry TO john, jane;

SHOW GRANTS FOR invoice_entry;

SET DEFAULT ROLE invoice_entry TO john, jane;

SET ROLE invoice_entry;

SELECT CURRENT_ROLE();

REVOKE UPDATE
ON invoice_line_items
FROM invoice_entry;

REVOKE invoice_entry FROM john;

DROP ROLE invoice_entry;