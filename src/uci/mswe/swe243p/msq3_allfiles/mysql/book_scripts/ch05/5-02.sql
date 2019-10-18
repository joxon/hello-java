INSERT INTO invoices VALUES
(115, 97, '456789', '2018-08-01', 8344.50, 0, 0, 1, '2018-08-31', NULL);

INSERT INTO invoices
    (vendor_id, invoice_number, invoice_total, terms_id, invoice_date, 
     invoice_due_date)
VALUES
    (97, '456789', 8344.50, 1, '2018-08-01', '2018-08-31');    
    
INSERT INTO invoices VALUES
(116, 97, '456701', '2018-08-02', 270.50, 0, 0, 1, '2018-09-01', NULL),
(117, 97, '456791', '2018-08-03', 4390.00, 0, 0, 1, '2018-09-02', NULL),
(118, 97, '456792', '2018-08-03', 565.60, 0, 0, 1, '2018-09-02', NULL);