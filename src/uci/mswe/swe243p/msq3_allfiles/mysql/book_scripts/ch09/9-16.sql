SELECT DISTINCT vendor_city, REGEXP_INSTR(vendor_city, ' ') AS space_index
FROM vendors
WHERE REGEXP_INSTR(vendor_city, ' ') > 0
ORDER BY vendor_city;

SELECT vendor_city, REGEXP_SUBSTR(vendor_city, '^SAN|LOS') AS city_match
FROM vendors
WHERE REGEXP_SUBSTR(vendor_city, '^SAN|LOS') IS NOT NULL;

SELECT vendor_name, vendor_address1, REGEXP_REPLACE(vendor_address1, 'STREET', 'St') AS new_address1
FROM Vendors
WHERE REGEXP_LIKE(vendor_address1, 'STREET');