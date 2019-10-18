USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE sql_error INT DEFAULT FALSE;
  BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
      SET sql_error = TRUE;

    INSERT INTO general_ledger_accounts VALUES (130, 'Cash');
    
    SELECT '1 row was inserted.' AS message;    
  END;
  
  IF sql_error = TRUE THEN
    SELECT 'Row was not inserted – SQL exception encountered.' AS message;	
  END IF;
END//

DELIMITER ;

CALL test();