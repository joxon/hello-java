USE ap;

DROP PROCEDURE IF EXISTS test;

DELIMITER //

CREATE PROCEDURE test()
BEGIN
  DECLARE max_invoice_total  DECIMAL(9,2);
  DECLARE min_invoice_total  DECIMAL(9,2);
  DECLARE percent_difference DECIMAL(9,4);
  DECLARE count_invoice_id   INT;
  DECLARE vendor_id_var      INT;
  
  SET vendor_id_var = 95;

  SELECT MAX(invoice_total), MIN(invoice_total), COUNT(invoice_id)
  INTO max_invoice_total, min_invoice_total, count_invoice_id
  FROM invoices WHERE vendor_id = vendor_id_var;

  SET percent_difference = (max_invoice_total - min_invoice_total) / 
                         min_invoice_total * 100;
  
  SELECT CONCAT('$', max_invoice_total) AS 'Maximum invoice', 
         CONCAT('$', min_invoice_total) AS 'Minimum invoice', 
         CONCAT('%', ROUND(percent_difference, 2)) AS 'Percent difference',
         count_invoice_id AS 'Number of invoices';
END//

DELIMITER ;

CALL test();