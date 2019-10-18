USE mysql;

SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS general_log_rotate;

DELIMITER //

CREATE EVENT general_log_rotate
ON SCHEDULE EVERY 1 MONTH
DO BEGIN
  DROP TABLE IF EXISTS general_log_old;
  
  CREATE TABLE general_log_old AS
  SELECT *
  FROM general_log;
  
  TRUNCATE general_log;
END//

DELIMITER ;

SELECT * FROM mysql.general_log;