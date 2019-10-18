USE ap;

DROP PROCEDURE IF EXISTS set_global_count;
DROP PROCEDURE IF EXISTS increment_global_count;

DELIMITER //

CREATE PROCEDURE set_global_count
(
  count_var INT
)
BEGIN
  SET @count = count_var;  
END//

CREATE PROCEDURE increment_global_count()
BEGIN
  SET @count = @count + 1;
END//

DELIMITER ;

CALL set_global_count(100);
CALL increment_global_count();

SELECT @count AS count_var
