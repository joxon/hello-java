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

  BEGIN
    DECLARE EXIT HANDLER FOR NOT FOUND
      SET row_not_found = TRUE;

    OPEN invoices_cursor;
    
    WHILE row_not_found = FALSE DO
      FETCH invoices_cursor 
      INTO vendor_name_var, invoice_number_var, balance_due_var;

      SET s = CONCAT(s, balance_due_var, '|',
                        invoice_number_var, '|',
                        vendor_name_var, '//');
    END WHILE;
  END;

  CLOSE invoices_cursor;    
  
  SELECT s AS message;
END//

-- Change statement delimiter from semicolon to double front slash
DELIMITER ;

CALL test();