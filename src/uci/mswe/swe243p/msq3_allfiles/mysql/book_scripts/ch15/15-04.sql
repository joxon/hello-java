USE ap;

DROP PROCEDURE IF EXISTS update_invoices_credit_total;

DELIMITER //

CREATE PROCEDURE update_invoices_credit_total
(
  invoice_id_param     INT,
  credit_total_param   DECIMAL(9,2)
)
BEGIN
  -- Validate paramater values
  IF credit_total_param < 0 THEN
    UPDATE `The credit_total column must be greater than or equal to 0.`
       SET x = 'This UPDATE statement raises an error';
  ELSEIF credit_total_param >= 1000 THEN
    SIGNAL SQLSTATE '22003'
      SET MESSAGE_TEXT = 
        'The credit_total column must be less than 1000.', 
      MYSQL_ERRNO = 1146;
  END IF;

  -- Set default values for parameters
  IF credit_total_param IS NULL THEN
    SET credit_total_param = 100;
  END IF;

  UPDATE invoices
  SET credit_total = credit_total_param
  WHERE invoice_id = invoice_id_param;  
END//

DELIMITER ;

CALL update_invoices_credit_total(56, NULL);

CALL update_invoices_credit_total(56, -100);

CALL update_invoices_credit_total(56, 1000);

CALL update_invoices_credit_total(56, 0);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;