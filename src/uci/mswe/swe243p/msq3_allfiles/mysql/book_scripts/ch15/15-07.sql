USE ap;

DROP PROCEDURE IF EXISTS select_invoices;

DELIMITER //

CREATE PROCEDURE select_invoices
(
  min_invoice_date_param   DATE,
  min_invoice_total_param  DECIMAL(9,2)
)
BEGIN
  DECLARE select_clause VARCHAR(200);
  DECLARE where_clause  VARCHAR(200);
  
  SET select_clause = "SELECT invoice_id, invoice_number, 
                       invoice_date, invoice_total 
                       FROM invoices ";      
  SET where_clause =  "WHERE ";
      
  IF min_invoice_date_param IS NOT NULL THEN
    SET where_clause = CONCAT(where_clause, 
       " invoice_date > '", min_invoice_date_param, "'");
  END IF;

  IF min_invoice_total_param IS NOT NULL THEN
    IF where_clause != "WHERE " THEN
      SET where_clause = CONCAT(where_clause, "AND ");
    END IF;
    SET where_clause = CONCAT(where_clause, 
       "invoice_total > ", min_invoice_total_param);
  END IF;

  IF where_clause = "WHERE " THEN
    SET @dynamic_sql = select_clause;
  ELSE
    SET @dynamic_sql = CONCAT(select_clause, where_clause);    
  END IF;
  
  PREPARE select_invoices_statement
  FROM @dynamic_sql;

  EXECUTE select_invoices_statement;
  
  DEALLOCATE PREPARE select_invoices_statement;  
END//

DELIMITER ;

CALL select_invoices('2018-07-25', 100);

CALL select_invoices('2018-07-25', NULL);

CALL select_invoices(NULL, 1000);

CALL select_invoices(NULL, NULL);
