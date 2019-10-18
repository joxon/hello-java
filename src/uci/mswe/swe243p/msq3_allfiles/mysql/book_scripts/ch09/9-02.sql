SELECT vendor_name, 
       CONCAT_WS(', ', vendor_contact_last_name, 
                 vendor_contact_first_name) AS contact_name,
       RIGHT(vendor_phone, 8) AS phone
FROM vendors
WHERE LEFT(vendor_phone, 4) = '(559'
ORDER BY contact_name;