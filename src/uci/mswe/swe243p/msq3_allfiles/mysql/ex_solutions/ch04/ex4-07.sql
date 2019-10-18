  SELECT vendor_name, vendor_state
  FROM vendors
  WHERE vendor_state = 'CA'
UNION
  SELECT vendor_name, 'Outside CA'
  FROM vendors
  WHERE vendor_state <> 'CA'
ORDER BY vendor_name
