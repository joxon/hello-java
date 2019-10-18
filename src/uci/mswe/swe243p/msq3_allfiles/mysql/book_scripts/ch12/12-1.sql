CREATE VIEW vendors_min AS
  SELECT vendor_name, vendor_state, vendor_phone
  FROM vendors;
  
SELECT * FROM vendors_min
WHERE vendor_state = 'CA'
ORDER BY vendor_name;

UPDATE vendors_min
SET vendor_phone = '(800) 555-3941'
WHERE vendor_name = 'Register of Copyrights';

DROP VIEW vendors_min;