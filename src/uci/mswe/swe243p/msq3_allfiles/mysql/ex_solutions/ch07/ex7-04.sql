SELECT vendor_name, i.invoice_id, invoice_sequence, line_item_amount
FROM vendors v JOIN invoices i
  ON v.vendor_id = i.vendor_id
JOIN invoice_line_items li
  ON i.invoice_id = li.invoice_id
WHERE i.invoice_id IN
      (SELECT DISTINCT invoice_id
       FROM invoice_line_items               
       WHERE invoice_sequence > 1)
ORDER BY vendor_name, i.invoice_id, invoice_sequence
