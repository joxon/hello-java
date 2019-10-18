USE ap;

DROP PROCEDURE IF EXISTS clear_invoices_credit_total;

DELIMITER //

CREATE PROCEDURE clear_invoices_credit_total
(
  invoice_id_param  INT
)
BEGIN
  UPDATE invoices
  SET credit_total = 0
  WHERE invoice_id = invoice_id_param;  
END//

DELIMITER ;

CALL clear_invoices_credit_total(56);

SELECT invoice_id, credit_total 
FROM invoices WHERE invoice_id = 56;

DROP PROCEDURE clear_invoices_credit_total;