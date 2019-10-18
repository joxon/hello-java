USE ap;

DROP FUNCTION IF EXISTS test_glaccounts_description;

DELIMITER //

CREATE FUNCTION test_glaccounts_description
(
   account_description_param VARCHAR(50)
)
RETURNS INT
DETERMINISTIC READS SQL DATA
BEGIN
  DECLARE account_description_var VARCHAR(50);

  SELECT account_description
  INTO account_description_var
  FROM general_ledger_accounts
  WHERE account_description = account_description_param;
  
  IF account_description_var IS NOT NULL THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
  
END//

DELIMITER ;

-- Test success: 
SELECT test_glaccounts_description('Book Inventory') AS message;

-- Test fail: 
SELECT test_glaccounts_description('Fail') AS message;