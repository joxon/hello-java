CREATE OR REPLACE VIEW balance_due_view AS 
  SELECT vendor_name, invoice_number, 
         invoice_total, payment_total, credit_total, 
         invoice_total - payment_total - credit_total AS balance_due
  FROM vendors JOIN invoices ON vendors.vendor_id = invoices.vendor_id
  WHERE invoice_total - payment_total - credit_total > 0;
  
UPDATE balance_due_view
SET credit_total = 300
WHERE invoice_number = '9982771';

UPDATE balance_due_view
SET balance_due = 0
WHERE invoice_number = '9982771';