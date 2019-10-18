UPDATE invoices
SET payment_date = '2018-09-21', 
    payment_total = 19351.18
WHERE invoice_number = '97/522';

UPDATE invoices
SET terms_id = 1
WHERE vendor_id = 95;

UPDATE invoices
SET credit_total = credit_total + 100
WHERE invoice_number = '97/522';