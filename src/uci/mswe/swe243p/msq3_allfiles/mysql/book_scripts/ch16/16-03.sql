USE ap;

DROP TABLE IF EXISTS invoices_audit;

CREATE TABLE invoices_audit
(
  vendor_id           INT             NOT NULL,
  invoice_number      VARCHAR(50)     NOT NULL,
  invoice_total       DECIMAL(9,2)    NOT NULL,
  action_type         VARCHAR(50)     NOT NULL,
  action_date         DATETIME        NOT NULL
);

DROP TRIGGER IF EXISTS invoices_after_insert;
DROP TRIGGER IF EXISTS invoices_after_delete;

DELIMITER //

CREATE TRIGGER invoices_after_insert
  AFTER INSERT ON invoices
  FOR EACH ROW
BEGIN
    INSERT INTO invoices_audit VALUES
    (NEW.vendor_id, NEW.invoice_number, NEW.invoice_total, 
    'INSERTED', NOW());
END//

CREATE TRIGGER invoices_after_delete
  AFTER DELETE ON invoices
  FOR EACH ROW
BEGIN
    INSERT INTO invoices_audit VALUES
    (OLD.vendor_id, OLD.invoice_number, OLD.invoice_total, 
    'DELETED', NOW());
END//

DELIMITER ;

-- make sure that there is at least one record to delete
INSERT INTO invoices VALUES 
(115, 34, 'ZXA-080', '2018-08-30', 14092.59, 0, 0, 3, '2018-09-30', NULL);

DELETE FROM invoices WHERE invoice_id = 115;

SELECT * FROM invoices_audit;

-- clean up
-- DELETE FROM invoices_audit;