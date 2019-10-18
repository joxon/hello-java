SELECT vendor_id, invoice_date, invoice_total,
       SUM(invoice_total) OVER() AS total_invoices,
       SUM(invoice_total) OVER(PARTITION BY vendor_id ORDER BY invoice_date
           ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS vendor_total
FROM invoices
WHERE invoice_date BETWEEN '2018-04-01' AND '2018-04-30';

SELECT vendor_id, invoice_date, invoice_total,
       SUM(invoice_total) OVER() AS total_invoices,
       SUM(invoice_total) OVER(PARTITION BY vendor_id ORDER BY invoice_date
           RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS vendor_total
FROM invoices
WHERE invoice_date BETWEEN '2018-04-01' AND '2018-04-30';

SELECT MONTH(invoice_date) AS month,
       SUM(invoice_total) AS total_invoices,
       ROUND(AVG(SUM(invoice_total)) OVER(ORDER BY MONTH(invoice_date)
           RANGE BETWEEN 1 PRECEDING AND 1 FOLLOWING), 2) AS 3_month_avg
FROM invoices
GROUP BY MONTH(invoice_date);