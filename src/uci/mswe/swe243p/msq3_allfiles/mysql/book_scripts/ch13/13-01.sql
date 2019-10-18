USE ap;

DROP PROCEDURE IF EXISTS test;

-- Change statement delimiter from semicolon to double front slash
DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE sum_balance_due_var DECIMAL(9, 2);

  SELECT SUM(invoice_total - payment_total - credit_total)
  INTO sum_balance_due_var
  FROM invoices 
  WHERE vendor_id = 95;
  -- for testing, the vendor with an ID of 37 has a balance due

  IF sum_balance_due_var > 0 THEN
    SELECT CONCAT('Balance due: $', sum_balance_due_var) AS message;
  ELSE
    SELECT 'Balance paid in full' AS message;
  END IF;  
END//

-- Change statement delimiter from semicolon to double front slash
DELIMITER ;

CALL test();