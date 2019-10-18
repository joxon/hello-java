USE ap;

START TRANSACTION;
  
SAVEPOINT before_invoice;

INSERT INTO invoices
VALUES (115, 34, 'ZXA-080', '2015-01-18', 
        14092.59, 0, 0, 3, '2015-04-18', NULL);

SAVEPOINT before_line_item1;

INSERT INTO invoice_line_items 
VALUES (115, 1, 160, 4447.23, 'HW upgrade');
  
SAVEPOINT before_line_item2;

INSERT INTO invoice_line_items 
VALUES (115, 2, 167, 9645.36,'OS upgrade');

-- SELECT invoice_id, invoice_sequence FROM invoice_line_items WHERE invoice_id = 115;

ROLLBACK TO SAVEPOINT before_line_item2;

-- SELECT invoice_id, invoice_sequence FROM invoice_line_items WHERE invoice_id = 115;

ROLLBACK TO SAVEPOINT before_line_item1;

-- SELECT invoice_id, invoice_sequence FROM invoice_line_items WHERE invoice_id = 115;

ROLLBACK TO SAVEPOINT before_invoice;

-- SELECT invoice_id, invoice_number FROM invoices WHERE invoice_id = 115;

COMMIT;