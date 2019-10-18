USE ap;

DROP PROCEDURE IF EXISTS insert_glaccount_with_test;

DELIMITER //

CREATE PROCEDURE insert_glaccount_with_test
(
  account_number_param        INT,   
  account_description_param   VARCHAR(50)
)
BEGIN
  IF test_glaccounts_description(account_description_param) = TRUE THEN
    SIGNAL SQLSTATE '23000' 
      SET MYSQL_ERRNO = 1062,
          MESSAGE_TEXT = 'Duplicate account description.';    
  ELSE
    INSERT INTO general_ledger_accounts
    VALUES (account_number_param, account_description_param);
  END IF;
END//

DELIMITER ;

-- Test fail: 
CALL insert_glaccount(700, 'Cash');

-- Test success: 
CALL insert_glaccount(700, 'Internet Services');

-- Clean up: 
DELETE FROM general_ledger_accounts WHERE account_number = 700;
