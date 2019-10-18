USE ap;

DROP TRIGGER IF EXISTS invoices_before_update;

DELIMITER //

CREATE TRIGGER invoices_before_update
  BEFORE UPDATE ON invoices
  FOR EACH ROW
BEGIN
  DECLARE sum_line_item_amount DECIMAL(9,2);
  
  SELECT SUM(line_item_amount) 
  INTO sum_line_item_amount
  FROM invoice_line_items
  WHERE invoice_id = NEW.invoice_id;
  
  IF sum_line_item_amount != NEW.invoice_total THEN
    SIGNAL SQLSTATE 'HY000'
      SET MESSAGE_TEXT = 'Line item total must match invoice total.';
  END IF;
END//

DELIMITER ;

UPDATE invoices
SET invoice_total = 600
WHERE invoice_id = 100;

SELECT invoice_id, invoice_total, credit_total, payment_total 
FROM invoices WHERE invoice_id = 100;
