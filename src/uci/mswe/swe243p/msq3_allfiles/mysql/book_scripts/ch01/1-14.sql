INSERT INTO invoices
  (vendor_id, invoice_number, invoice_date, 
   invoice_total, terms_id, invoice_due_date)
VALUES 
  (12, '3289175', '2014-07-18', 165, 3, '2014-08-17');

UPDATE invoices
SET credit_total = 35.89
WHERE invoice_number = '367447';

UPDATE invoices
SET invoice_due_date = DATE_ADD(invoice_due_date, INTERVAL 30 DAY)
WHERE terms_id = 4;


DELETE FROM invoices
WHERE invoice_number = '4-342-8069';

DELETE FROM invoices
WHERE invoice_total - payment_total - credit_total = 0;