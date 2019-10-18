USE ap;

DROP FUNCTION IF EXISTS get_sum_balance_due;

DELIMITER //

CREATE FUNCTION get_sum_balance_due
(
   vendor_id_param INT
)
RETURNS DECIMAL(9,2)
DETERMINISTIC READS SQL DATA
BEGIN
  DECLARE sum_balance_due_var DECIMAL(9,2);
  
  SELECT SUM(get_balance_due(invoice_id))
  INTO sum_balance_due_var 
  FROM invoices
  WHERE vendor_id = vendor_id_param;
  
  RETURN sum_balance_due_var;
END//

DELIMITER ;

SELECT vendor_id, invoice_number, 
       get_balance_due(invoice_id) AS balance_due, 
       get_sum_balance_due(vendor_id) AS sum_balance_due
FROM invoices
WHERE vendor_id = 37;

DROP FUNCTION get_sum_balance_due;