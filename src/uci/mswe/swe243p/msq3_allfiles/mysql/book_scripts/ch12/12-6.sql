CREATE OR REPLACE VIEW ibm_invoices AS
  SELECT invoice_number, invoice_date, invoice_total
  FROM invoices
  WHERE vendor_id = 34;
  
INSERT INTO ibm_invoices
  (invoice_number, invoice_date, invoice_total)
VALUES 
  ('RA23988', '2018-07-31', 417.34);
  
DELETE FROM ibm_invoices
WHERE invoice_number = 'Q545443';

DELETE FROM invoice_line_items
WHERE invoice_id = (SELECT invoice_id FROM invoices 
                    WHERE invoice_number = 'Q545443');

DELETE FROM ibm_invoices
WHERE invoice_number = 'Q545443';