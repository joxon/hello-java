USE ex;

-- Execute each statement one at a time.
-- Alternate with Transaction B, C, and D as described.

START TRANSACTION;

-- lock row with rep_id of 2 in parent table
SELECT * FROM sales_reps WHERE rep_id = 2 FOR SHARE;  

-- Transaction B waits for transaction A to finish
-- Transaction C returns an error immediately
-- Transaction D skips any locked rows and returns the other rows immediately

 -- insert row with rep_id of 2 into child table
INSERT INTO sales_totals (rep_id, sales_year, sales_total) 
VALUES (2, 2019, 138193.69);

COMMIT;  -- Transaction B executes now

-- clean up sales_totals table
DELETE FROM sales_totals WHERE rep_id = 2 AND sales_year = 2019;