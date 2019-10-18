USE ap;

DROP PROCEDURE IF EXISTS test;

-- Change statement delimiter from semicolon to double front slash
DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE vendor_name_var     VARCHAR(50);
  DECLARE invoice_number_var  VARCHAR(50);
  DECLARE balance_due_var     DECIMAL(9,2);

  DECLARE s                   VARCHAR(400)   DEFAULT '';
  DECLARE row_not_found       INT            DEFAULT FALSE;
  
  DECLARE invoices_cursor CURSOR FOR
    SELECT vendor_name, invoice_number,
      invoice_total - payment_total - credit_total AS balance_due
    FROM vendors v JOIN invoices i
      ON v.vendor_id = i.vendor_id
    WHERE invoice_total - payment_total - credit_total >= 5000
    ORDER BY balance_due DESC;

  -- Loop 1
  BEGIN
    DECLARE EXIT HANDLER FOR NOT FOUND
      SET row_not_found = TRUE;

    OPEN invoices_cursor;
    
    SET s = CONCAT(s, '$20,000 or More: ');      
                             
    WHILE row_not_found = FALSE DO
      FETCH invoices_cursor 
      INTO vendor_name_var, invoice_number_var, balance_due_var;

      IF balance_due_var >= 20000 THEN
        SET s = CONCAT(s, balance_due_var, '|',
                          invoice_number_var, '|',
                          vendor_name_var, '//');
      END IF;
    END WHILE;    
  END;

  CLOSE invoices_cursor;    

  -- Loop 2
  SET row_not_found = FALSE;
  BEGIN
    DECLARE EXIT HANDLER FOR NOT FOUND
      SET row_not_found = TRUE;

    OPEN invoices_cursor;
    
    SET s = CONCAT(s, '$10,000 to $20,000: ');
        
    WHILE row_not_found = FALSE DO
      FETCH invoices_cursor 
      INTO vendor_name_var, invoice_number_var, balance_due_var;

      IF balance_due_var >= 10000 AND balance_due_var < 20000 THEN
        SET s = CONCAT(s, balance_due_var, '|',
                          invoice_number_var, '|',
                          vendor_name_var, '//');
      END IF;
    END WHILE;    
  END;

  CLOSE invoices_cursor;    

  -- Loop 3
  SET row_not_found = FALSE;
  BEGIN
    DECLARE EXIT HANDLER FOR NOT FOUND
      SET row_not_found = TRUE;

    OPEN invoices_cursor;
    
    SET s = CONCAT(s, '$5,000 to $10,000: ');
        
    WHILE row_not_found = FALSE DO
      FETCH invoices_cursor 
      INTO vendor_name_var, invoice_number_var, balance_due_var;

      IF balance_due_var >= 5000 AND balance_due_var < 10000 THEN
        SET s = CONCAT(s, balance_due_var, '|',
                          invoice_number_var, '|',
                          vendor_name_var, '//');
      END IF;
    END WHILE;    
  END;

  CLOSE invoices_cursor;    
  
  -- Display the string variable
  SELECT s AS message;
END//
    
-- Change statement delimiter from semicolon to double front slash
DELIMITER ;

CALL test();