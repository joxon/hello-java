USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE sql_error INT DEFAULT FALSE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET sql_error = TRUE;

  START TRANSACTION;
  
  INSERT INTO invoices
  VALUES (115, 34, 'ZXA-080', '2014-06-30', 
          14092.59, 0, 0, 3, '2014-09-30', NULL);

  INSERT INTO invoice_line_items 
  VALUES (115, 1, 160, 4447.23, 'HW upgrade');
  
  INSERT INTO invoice_line_items 
  VALUES (115, 2, 167, 9645.36, 'OS upgrade');
  
  IF sql_error = FALSE THEN
    COMMIT;
    SELECT 'The transaction was committed.';
  ELSE
    ROLLBACK;
    SELECT 'The transaction was rolled back.';
  END IF;
END//

DELIMITER ;

CALL test();

-- Check data
SELECT invoice_id, invoice_number
FROM invoices WHERE invoice_id = 115;

SELECT invoice_id, invoice_sequence, line_item_description
FROM invoice_line_items WHERE invoice_id = 115;

-- Clean up
DELETE FROM invoice_line_items WHERE invoice_id = 115;
DELETE FROM invoices WHERE invoice_id = 115;