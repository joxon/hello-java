SELECT ROW_NUMBER() OVER(ORDER BY vendor_name) AS 'row_number', vendor_name
FROM vendors;

SELECT ROW_NUMBER() OVER(PARTITION BY vendor_state 
    ORDER BY vendor_name) AS 'row_number', vendor_name, vendor_state
FROM vendors;

SELECT RANK() OVER (ORDER BY invoice_total) AS 'rank', 
       DENSE_RANK() OVER (ORDER BY invoice_total) AS 'dense_rank',
       invoice_total, invoice_number
FROM invoices;

SELECT terms_description, 
    NTILE(2) OVER (ORDER BY terms_id) AS tile2, 
    NTILE(3) OVER (ORDER BY terms_id) AS tile3, 
    NTILE(4) OVER (ORDER BY terms_id) AS tile4 
FROM terms;
