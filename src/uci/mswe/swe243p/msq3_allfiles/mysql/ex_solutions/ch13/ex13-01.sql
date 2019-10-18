USE ap;

DROP PROCEDURE IF EXISTS test;

-- Change statement delimiter from semicolon to double front slash
DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE invoice_count   INT;

  SELECT COUNT(*)
  INTO invoice_count
  FROM invoices
  WHERE invoice_total - payment_total - credit_total >= 5000;
  
  SELECT CONCAT(invoice_count, ' invoices exceed $5000.') AS message;
END//

-- Change statement delimiter from semicolon to double front slash
DELIMITER ;

CALL test();