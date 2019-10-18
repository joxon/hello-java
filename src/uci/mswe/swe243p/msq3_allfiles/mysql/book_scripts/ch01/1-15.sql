select invoice_number, invoice_date, invoice_total, 
payment_total, credit_total, invoice_total - payment_total - 
credit_total as balance_due from invoices where invoice_total - 
payment_total - credit_total > 0 order by invoice_date;

SELECT invoice_number, invoice_date, invoice_total,
    payment_total, credit_total,
    invoice_total - payment_total - credit_total AS balance_due
FROM invoices 
WHERE invoice_total - payment_total - credit_total > 0
ORDER BY invoice_date;

/*
Author: Joel Murach
Date: 8/22/2014
*/
SELECT invoice_number, invoice_date, invoice_total,
    invoice_total - payment_total - credit_total AS balance_due
FROM invoices; 

-- The fourth column calculates the balance due
SELECT invoice_number, invoice_date, invoice_total,
    invoice_total - payment_total - credit_total AS balance_due
FROM invoices;