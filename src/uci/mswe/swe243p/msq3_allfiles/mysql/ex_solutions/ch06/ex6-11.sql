SELECT MONTH(invoice_date) AS month, SUM(invoice_total) AS total_invoices,
       ROUND(AVG(SUM(invoice_total)) OVER(ORDER BY MONTH(invoice_date)
           RANGE BETWEEN 3 PRECEDING AND CURRENT ROW), 2) AS 4_month_avg
FROM invoices
GROUP BY MONTH(invoice_date)