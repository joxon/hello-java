SELECT CONCAT(vendor_name, CHAR(13,10), vendor_address1, CHAR(13,10),
       vendor_city, ', ', vendor_state, ' ', vendor_zip_code)
FROM vendors
WHERE vendor_id = 1;