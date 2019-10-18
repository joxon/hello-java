USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE column_cannot_be_null TINYINT DEFAULT FALSE;

  DECLARE CONTINUE HANDLER FOR 1048
    SET column_cannot_be_null = TRUE;

  UPDATE invoices
  SET invoice_due_date = NULL
  WHERE invoice_id = 1;
  
  IF column_cannot_be_null = TRUE THEN
    SELECT 'Row was not updated - column cannot be null.' AS message;
  ELSE
    SELECT '1 row was updated.' AS message;    
  END IF;

END//

DELIMITER ;

CALL test();
