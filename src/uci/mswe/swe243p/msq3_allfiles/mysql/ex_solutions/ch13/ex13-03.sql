DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE i         INT DEFAULT 10;
  DECLARE factorial INT DEFAULT 10;

  WHILE i > 1 DO
    SET factorial = factorial * (i - 1);
    SET i = i - 1;
  END WHILE;
  
  SELECT CONCAT('The factorial of 10 is: ', FORMAT(factorial,0), '.') AS message;

END//

DELIMITER ;

CALL test();
