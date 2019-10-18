USE ap;

DROP PROCEDURE IF EXISTS update_invoices_credit_total;

DELIMITER //

CREATE PROCEDURE update_invoices_credit_total
(
  invoice_id_param     INT,
  credit_total_param   DECIMAL(9,2)
)
BEGIN
  DECLARE sql_error INT DEFAULT FALSE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET sql_error = TRUE;
    
  -- Set default values for NULL values
  IF credit_total_param IS NULL THEN
    SET credit_total_param = 100;
  END IF;

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

-- call with param
CALL update_invoices_credit_total(56, 200);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;

-- call without param
CALL update_invoices_credit_total(56, NULL);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;

-- reset data
CALL update_invoices_credit_total(56, 0);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;