USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  SELECT 'This is a test.' AS message;
END//

DELIMITER ;

CALL test();