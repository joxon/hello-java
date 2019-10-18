USE ap;

DROP PROCEDURE IF EXISTS insert_invoice;

DELIMITER //

CREATE PROCEDURE insert_invoice
(
  vendor_id_param        INT,
  invoice_number_param   VARCHAR(50),
  invoice_date_param     DATE,
  invoice_total_param    DECIMAL(9,2),
  terms_id_param         INT,
  invoice_due_date_param DATE
)
BEGIN
  DECLARE terms_id_var           INT;
  DECLARE invoice_due_date_var   DATE;  
  DECLARE terms_due_days_var     INT;

  -- Validate paramater values
  IF invoice_total_param < 0 THEN
    SIGNAL SQLSTATE '22003'
      SET MESSAGE_TEXT = 'The invoice_total column must be a positive number.', 
      MYSQL_ERRNO = 1264;
  ELSEIF invoice_total_param >= 1000000 THEN
    SIGNAL SQLSTATE '22003'
      SET MESSAGE_TEXT = 'The invoice_total column must be less than 1,000,000.', 
      MYSQL_ERRNO = 1264;
  END IF;

  -- Set default values for parameters
  IF terms_id_param IS NULL THEN
    SELECT default_terms_id INTO terms_id_var
    FROM vendors WHERE vendor_id = vendor_id_param;
  ELSE
    SET terms_id_var = terms_id_param;
  END IF;
  IF invoice_due_date_param IS NULL THEN
    SELECT terms_due_days INTO terms_due_days_var
      FROM terms WHERE terms_id = terms_id_var;
    SELECT DATE_ADD(invoice_date_param, INTERVAL terms_due_days_var DAY) 
      INTO invoice_due_date_var;
  ELSE
    SET invoice_due_date_var = invoice_due_date_param;
  END IF;

  INSERT INTO invoices
         (vendor_id, invoice_number, invoice_date, 
          invoice_total, terms_id, invoice_due_date)
  VALUES (vendor_id_param, invoice_number_param, invoice_date_param, 
          invoice_total_param, terms_id_var, invoice_due_date_var);
END//

DELIMITER ;

-- test
CALL insert_invoice(34, 'ZXA-080', '2018-01-18', 14092.59, 
                    3, '2018-03-18');
CALL insert_invoice(34, 'ZXA-082', '2018-01-18', 14092.59,
                    NULL, NULL);

-- this statement raises an error
CALL insert_invoice(34, 'ZXA-083', '2018-01-18', -14092.59,
                    NULL, NULL);

-- clean up
SELECT * FROM invoices WHERE invoice_id >= 115;

DELETE FROM invoices WHERE invoice_id >= 115;