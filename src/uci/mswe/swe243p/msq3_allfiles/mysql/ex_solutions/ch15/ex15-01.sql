USE ap;

DROP PROCEDURE IF EXISTS insert_glaccount;

DELIMITER //

CREATE PROCEDURE insert_glaccount
(
  account_number_param        INT,   
  account_description_param   VARCHAR(50)
)
BEGIN
  INSERT INTO general_ledger_accounts
  VALUES (account_number_param, account_description_param);
END//

DELIMITER ;

-- Test fail: 
CALL insert_glaccount(700, 'Cash');

-- Test success: 
CALL insert_glaccount(700, 'Internet Services');

-- Clean up: 
DELETE FROM general_ledger_accounts WHERE account_number = 700;
