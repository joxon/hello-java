SELECT invoice_date, payment_date,
       SUM(invoice_total) AS invoice_total,
       SUM(invoice_total - credit_total - payment_total) AS balance_due
FROM invoices
WHERE invoice_date BETWEEN '2018-07-24' AND '2018-07-31'
GROUP BY invoice_date, payment_date WITH ROLLUP;

SELECT IF(GROUPING(invoice_date) = 1, 'Grand totals', invoice_date) AS invoice_date,
       IF(GROUPING(payment_date) = 1, 'Invoice date totals', payment_date) AS payment_date,
       SUM(invoice_total) AS invoice_total,
       SUM(invoice_total - credit_total - payment_total) AS balance_due
FROM invoices
WHERE invoice_date BETWEEN '2018-07-24' AND '2018-07-31'
GROUP BY invoice_date, payment_date WITH ROLLUP;

SELECT IF(GROUPING(invoice_date) = 1, 'Grand totals', invoice_date) AS invoice_date,
       IF(GROUPING(payment_date) = 1, 'Invoice date totals', payment_date) AS payment_date,
       SUM(invoice_total) AS invoice_total,
       SUM(invoice_total - credit_total - payment_total) AS balance_due
FROM invoices
WHERE invoice_date BETWEEN '2018-07-24' AND '2018-07-31'
GROUP BY invoice_date, payment_date WITH ROLLUP
HAVING GROUPING(invoice_date) = 1 OR GROUPING(payment_date) = 1;