-- Transaction A

-- Execute each statement one at a time.
-- Alternate with Transaction B (14-03b.sql) as described.

SELECT invoice_id, credit_total FROM invoices WHERE invoice_id = 6;

START TRANSACTION;
  
UPDATE invoices SET credit_total = credit_total + 100 WHERE invoice_id = 6;

-- The SELECT statement in Transaction B won't show the updated data.
-- The UPDATE statement in Transaction B will wait for transaction A to finish.

COMMIT;

-- The SELECT statement in Transaction B will display the updated data.
-- The UPDATE statement in Transaction B will execute immdediately.

-- clean up code
UPDATE invoices SET credit_total = 0 WHERE invoice_id = 6;