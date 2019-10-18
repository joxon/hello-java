-- Use a second connection to execute these statements!
-- Otherwise, they won't work as described.

USE ex;

-- Transaction B
START TRANSACTION;
SELECT * FROM sales_reps WHERE rep_id < 5 FOR UPDATE;
COMMIT;

-- Transaction C
START TRANSACTION;
SELECT * FROM sales_reps WHERE rep_id < 5 FOR UPDATE NOWAIT;
COMMIT;

-- Transaction D
START TRANSACTION;
SELECT * FROM sales_reps WHERE rep_id < 5 FOR UPDATE SKIP LOCKED;
COMMIT;
