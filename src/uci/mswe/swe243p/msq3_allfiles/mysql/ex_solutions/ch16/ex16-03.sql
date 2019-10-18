USE ap;

-- execute if the event scheduler isn't on
-- SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS minute_test;

DELIMITER //

CREATE EVENT minute_test
ON SCHEDULE EVERY 1 MINUTE
DO BEGIN
    INSERT INTO invoices_audit VALUES
    (9999, 'test', 999.99, 'INSERTED', NOW());
END//

DELIMITER ;

SHOW EVENTS LIKE 'min%';

SELECT * FROM invoices_audit;

DROP EVENT minute_test;
