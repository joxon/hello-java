USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE duplicate_entry_for_key INT DEFAULT FALSE;

  DECLARE CONTINUE HANDLER FOR 1062
    SET duplicate_entry_for_key = TRUE;

  INSERT INTO general_ledger_accounts VALUES (130, 'Cash');
  
  IF duplicate_entry_for_key = TRUE THEN
    SELECT 'Row was not inserted - duplicate key encountered.' AS message;
  ELSE
    SELECT '1 row was inserted.' AS message;    
  END IF;

END//

DELIMITER ;

CALL test();