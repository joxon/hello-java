USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE first_invoice_due_date DATE;

  SELECT MIN(invoice_due_date)
  INTO first_invoice_due_date
  FROM invoices
  WHERE invoice_total - payment_total - credit_total > 0;

  IF first_invoice_due_date < NOW() THEN
    SELECT 'Outstanding invoices are overdue!';
  ELSEIF first_invoice_due_date = SYSDATE() THEN
    SELECT 'Outstanding invoices are due today!';
  ELSE
    SELECT 'No invoices are overdue.';
  END IF;

  -- the IF statement rewritten as a Searched CASE statement
  /*
  CASE  
    WHEN first_invoice_due_date < NOW() THEN
      SELECT 'Outstanding invoices overdue!' AS Message;
    WHEN first_invoice_due_date = NOW() THEN
      SELECT 'Outstanding invoices are due today!' AS Message;
    ELSE
      SELECT 'No invoices are overdue.' AS Message;
  END CASE;
  */
  
END//

DELIMITER ;

CALL test();