USE ap;

DROP FUNCTION IF EXISTS get_balance_due;

DELIMITER //

CREATE FUNCTION get_balance_due
(
   invoice_id_param INT
)
RETURNS DECIMAL(9,2)
DETERMINISTIC READS SQL DATA
BEGIN
  DECLARE balance_due_var DECIMAL(9,2);
  
  SELECT invoice_total - payment_total - credit_total
  INTO balance_due_var
  FROM invoices
  WHERE invoice_id = invoice_id_param;
  
  RETURN balance_due_var;
END//

DELIMITER ;

SELECT vendor_id, invoice_number, 
       get_balance_due(invoice_id) AS balance_due 
FROM invoices
WHERE vendor_id = 37;