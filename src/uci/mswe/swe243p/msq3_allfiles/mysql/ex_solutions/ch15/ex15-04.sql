USE ap;

DROP PROCEDURE IF EXISTS insert_terms;

DELIMITER //

CREATE PROCEDURE insert_terms
(
  terms_due_days_param      INT,
  terms_description_param    VARCHAR(50)
)
BEGIN  
  DECLARE sql_error TINYINT DEFAULT FALSE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET sql_error = TRUE;
    
  -- Set default values for NULL values
  IF terms_description_param IS NULL THEN
    SET terms_description_param = CONCAT('Net due ', terms_due_days_param, ' days');
  END IF;

  START TRANSACTION;
  
  INSERT INTO terms
  VALUES (DEFAULT, terms_description_param, terms_due_days_param);
  
  IF sql_error = FALSE THEN
    COMMIT;
  ELSE
    ROLLBACK;
  END IF;
END//

DELIMITER ;

CALL insert_terms (120, 'Net due 120 days');
CALL insert_terms (150, NULL);

-- Clean up
DELETE FROM terms WHERE terms_id > 5;