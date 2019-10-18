USE ap;

DROP PROCEDURE IF EXISTS test;

-- Change statement delimiter from semicolon to double front slash
DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE count_balance_due   INT;
  DECLARE total_balance_due   DECIMAL(9,2);

  SELECT COUNT(*), SUM(invoice_total - payment_total - credit_total)
  INTO count_balance_due, total_balance_due
  FROM invoices
  WHERE invoice_total - payment_total - credit_total > 0;

  IF total_balance_due >= 30000 THEN
    SELECT count_balance_due AS count_balance_due, 
           total_balance_due AS total_balance_due;
  ELSE
    SELECT 'Total balance due is less than $30,000.' AS message;
  END IF;
END//

-- Change statement delimiter from semicolon to double front slash
DELIMITER ;

CALL test();