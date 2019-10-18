USE ap;

DROP TRIGGER IF EXISTS invoices_after_update;

DELIMITER //

CREATE TRIGGER invoices_after_update
  AFTER UPDATE ON invoices
  FOR EACH ROW
BEGIN
    INSERT INTO invoices_audit VALUES
    (OLD.vendor_id, OLD.invoice_number, OLD.invoice_total, 
    'UPDATED', NOW());
END//

DELIMITER ;

UPDATE invoices
SET payment_total = 100
WHERE invoice_id = 112;

SELECT * FROM invoices_audit;

-- clean up
UPDATE invoices
SET payment_total = 0
WHERE invoice_id = 112;

-- clean up
-- DELETE FROM invoices_audit WHERE vendor_id = 110 LIMIT 100;