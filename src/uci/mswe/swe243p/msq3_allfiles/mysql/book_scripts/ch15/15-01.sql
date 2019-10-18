USE ap;

DROP PROCEDURE IF EXISTS update_invoices_credit_total;

DELIMITER //

CREATE PROCEDURE update_invoices_credit_total
(
  invoice_id_param      INT,
  credit_total_param    DECIMAL(9,2) 
)
BEGIN
  DECLARE sql_error INT DEFAULT FALSE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET sql_error = TRUE;

  START TRANSACTION;  

  UPDATE invoices
  SET credit_total = credit_total_param
  WHERE invoice_id = invoice_id_param;

  IF sql_error = FALSE THEN
    COMMIT;
  ELSE
    ROLLBACK;
  END IF;
END//

DELIMITER ;

-- Use the CALL statement
CALL update_invoices_credit_total(56, 200);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;

-- Use the CALL statement within a stored procedure
DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  CALL update_invoices_credit_total(56, 300);
END//

DELIMITER ;

CALL test();

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;

-- Reset data to original value
CALL update_invoices_credit_total(56, 0);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;