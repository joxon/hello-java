USE ap;

DROP PROCEDURE IF EXISTS update_invoices_credit_total;

DELIMITER //

CREATE PROCEDURE update_invoices_credit_total
(
  IN invoice_id_param    INT,
  IN credit_total_param  DECIMAL(9,2), 
  OUT update_count       INT
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
    SET update_count = 1;
    COMMIT;
  ELSE
    SET update_count = 0;
    ROLLBACK;
  END IF;
END//

DELIMITER ;

CALL update_invoices_credit_total(56, 200, @row_count);

CALL update_invoices_credit_total(56, 0, @row_count);

SELECT CONCAT('row_count: ', @row_count) AS update_count;