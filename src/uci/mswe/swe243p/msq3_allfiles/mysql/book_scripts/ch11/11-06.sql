ALTER TABLE vendors
ADD PRIMARY KEY (vendor_id);

ALTER TABLE invoices
ADD CONSTRAINT invoices_fk_vendors
FOREIGN KEY (vendor_id) REFERENCES vendors (vendor_id);

ALTER TABLE vendors
DROP PRIMARY KEY;

ALTER TABLE invoices
DROP FOREIGN KEY invoices_fk_vendors;