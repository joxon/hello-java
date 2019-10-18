USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE duplicate_entry_for_key INT DEFAULT FALSE;
  DECLARE column_cannot_be_null   INT DEFAULT FALSE;
  DECLARE sql_exception           INT DEFAULT FALSE;
  
  BEGIN
    DECLARE EXIT HANDLER FOR 1062
      SET duplicate_entry_for_key = TRUE;
    DECLARE EXIT HANDLER FOR 1048
      SET column_cannot_be_null = TRUE;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
      SET sql_exception = TRUE;

    INSERT INTO general_ledger_accounts VALUES (NULL, 'Test');
    
    SELECT '1 row was inserted.' AS message;    
  END;
  
  IF duplicate_entry_for_key = TRUE THEN
    SELECT 'Row was not inserted - duplicate key encountered.' AS message;
  ELSEIF column_cannot_be_null = TRUE THEN
    SELECT 'Row was not inserted - column cannot be null.' AS message;
  ELSEIF sql_exception = TRUE THEN
    SELECT 'Row was not inserted – SQL exception encountered.' AS message;	
  END IF;
END//

DELIMITER ;

CALL test();