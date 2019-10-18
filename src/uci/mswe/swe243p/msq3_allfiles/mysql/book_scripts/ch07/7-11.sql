SELECT t1.vendor_state, vendor_name, t1.sum_of_invoices
FROM
    (
        -- invoice totals by vendor
        SELECT vendor_state, vendor_name,
            SUM(invoice_total) AS sum_of_invoices
        FROM vendors v JOIN invoices i 
            ON v.vendor_id = i.vendor_id
        GROUP BY vendor_state, vendor_name
    ) t1
    JOIN
        (
            -- top invoice totals by state
            SELECT vendor_state,  
                   MAX(sum_of_invoices) AS sum_of_invoices
            FROM
            (
                 -- invoice totals by vendor
                 SELECT vendor_state, vendor_name,
                     SUM(invoice_total) AS sum_of_invoices
                 FROM vendors v JOIN invoices i 
                     ON v.vendor_id = i.vendor_id
                 GROUP BY vendor_state, vendor_name
            ) t2
            GROUP BY vendor_state
        ) t3
    ON t1.vendor_state = t3.vendor_state AND 
       t1.sum_of_invoices = t3.sum_of_invoices
ORDER BY vendor_state;