SELECT vendor_name, 
       IF(vendor_city = 'Fresno', 'Yes', 'No') AS is_city_fresno
FROM vendors;

SELECT payment_date,
       IFNULL(payment_date, 'No Payment') AS new_date
FROM invoices;

SELECT payment_date,
       COALESCE(payment_date, 'No Payment') AS new_date
FROM invoices;