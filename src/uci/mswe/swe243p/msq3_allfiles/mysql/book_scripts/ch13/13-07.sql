USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE s VARCHAR(400) DEFAULT '';

  -- WHILE loop
  WHILE i < 4 DO
    SET s = CONCAT(s, 'i=', i, ' | ');
    SET i = i + 1;
  END WHILE;

  -- REPEAT loop
  /*
  REPEAT
    SET s = CONCAT(s, 'i=', i, ' | ');
    SET i = i + 1;
  UNTIL i = 4
  END REPEAT;
  */

  -- LOOP with LEAVE statement
  /*
  testLoop : LOOP
    SET s = CONCAT(s, 'i=', i, ' | ');
    SET i = i + 1;

    IF i = 4 THEN
       LEAVE testLoop;
    END IF;        
  END LOOP testLoop;
  */
  
  SELECT s AS message;

END//

DELIMITER ;

CALL test();