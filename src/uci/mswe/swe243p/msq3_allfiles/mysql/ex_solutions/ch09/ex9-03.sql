SELECT vendor_name,
    UPPER(vendor_name) AS vendor_name_upper,
    vendor_phone,
    SUBSTRING(vendor_phone, 11) AS last_digits,
    REPLACE(REPLACE(REPLACE(vendor_phone, '(', ''), ') ', '.'), '-', '.') AS phone_with_dots,
    IF(LOCATE(' ', vendor_name) = 0,
        '',
		IF(LOCATE(' ', vendor_name, LOCATE(' ', vendor_name) + 1) = 0,
			SUBSTRING(vendor_name, LOCATE(' ', vendor_name) + 1),
			SUBSTRING(vendor_name,
				LOCATE(' ', vendor_name) + 1,
                LOCATE(' ', vendor_name, LOCATE(' ', vendor_name) + 1) - LOCATE(' ', vendor_name))))
    AS second_word
FROM vendors