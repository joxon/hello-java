SELECT 
    invoice_date,
    COUNT(*) AS invoice_qty,
    SUM(invoice_total) AS invoice_sum
FROM invoices
GROUP BY invoice_date
HAVING invoice_date BETWEEN '2018-05-01' AND '2018-05-31'
    AND COUNT(*) > 1
    AND SUM(invoice_total) > 100
ORDER BY invoice_date DESC;

SELECT 
    invoice_date,
    COUNT(*) AS invoice_qty,
    SUM(invoice_total) AS invoice_sum
FROM invoices
WHERE invoice_date BETWEEN '2018-05-01' AND '2018-05-31'
GROUP BY invoice_date
HAVING COUNT(*) > 1 
    AND SUM(invoice_total) > 100
ORDER BY invoice_date DESC;