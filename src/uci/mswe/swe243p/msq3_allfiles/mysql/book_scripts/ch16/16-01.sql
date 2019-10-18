USE ap;

DROP TRIGGER IF EXISTS vendors_before_update;

DELIMITER //

CREATE TRIGGER vendors_before_update
  BEFORE UPDATE ON vendors
  FOR EACH ROW 
BEGIN
  SET NEW.vendor_state = UPPER(NEW.vendor_state);
END//

DELIMITER ;

UPDATE vendors
SET vendor_state = 'wi'
WHERE vendor_id = 1;

SELECT vendor_name, vendor_state
FROM vendors
WHERE vendor_id = 1;