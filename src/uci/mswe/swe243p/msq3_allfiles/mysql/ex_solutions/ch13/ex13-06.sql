DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE j INT;
  DECLARE divisor_found TINYINT DEFAULT TRUE;
  DECLARE s VARCHAR(400) DEFAULT '';

  WHILE i < 100 DO
    SET j = i - 1;
    WHILE j > 1 DO
      IF i % j = 0 THEN
        SET j = 1;
        SET divisor_found = TRUE;
      ELSE
        SET j = j - 1;            
      END IF;
    END WHILE;
    IF divisor_found != TRUE THEN
      SET s = CONCAT(s, i, ' | ');
    END IF;
    SET i = i + 1;
    SET divisor_found = FALSE;
  END WHILE;

SELECT s AS 'Prime numbers < 100';

END//


DELIMITER ;

CALL test();

