USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  INSERT INTO general_ledger_accounts VALUES (130, 'Cash');

  SELECT '1 row was inserted.';  
END//

DELIMITER ;

CALL test();