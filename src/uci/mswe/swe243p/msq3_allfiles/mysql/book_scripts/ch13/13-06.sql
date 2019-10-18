USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE terms_id_var INT;

  SELECT terms_id INTO terms_id_var 
  FROM invoices WHERE invoice_id = 4;

  CASE terms_id_var
    WHEN 1 THEN 
      SELECT 'Net due 10 days' AS Terms;
    WHEN 2 THEN 
      SELECT 'Net due 20 days' AS Terms;      
    WHEN 3 THEN 
      SELECT 'Net due 30 days' AS Terms;      
    ELSE
      SELECT 'Net due more than 30 days' AS Terms;
  END CASE;

  -- rewritten as a Searched CASE statement
  /*
  CASE 
    WHEN terms_id_var = 1 THEN 
      SELECT 'Net due 10 days' AS Terms;      
    WHEN terms_id_var = 2 THEN 
      SELECT 'Net due 20 days' AS Terms;      
    WHEN terms_id_var = 3 THEN 
      SELECT 'Net due 30 days' AS Terms;      
    ELSE
      SELECT 'Net due more than 30 days' AS Terms;      
  END CASE;
  */

END//

DELIMITER ;

CALL test();