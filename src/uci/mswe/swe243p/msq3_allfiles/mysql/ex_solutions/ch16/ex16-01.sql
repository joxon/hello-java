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
  ELSEIF NEW.payment_total + NEW.credit_total > NEW.invoice_total THEN
    SIGNAL SQLSTATE 'HY000'
      SET MESSAGE_TEXT = 'Payment total + credit total can not be greater than invoice total.';
  END IF;
END//

DELIMITER ;

UPDATE invoices
SET payment_total = 0, credit_total = 0
WHERE invoice_id = 112;

SELECT invoice_id, invoice_total, credit_total, payment_total
FROM invoices
WHERE invoice_id = 112;

-- causes an error because payment_total + credit_total would be greater than invoice_total
UPDATE invoices
SET payment_total = 10000, credit_total = 1000
WHERE invoice_id = 112;