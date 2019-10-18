CREATE TABLE invoices
(
  invoice_id            INT            PRIMARY KEY   AUTO_INCREMENT,
  vendor_id             INT            NOT NULL,
  invoice_number        VARCHAR(50)    NOT NULL,
  invoice_date          DATE           NOT NULL,
  invoice_total         DECIMAL(9,2)   NOT NULL,
  payment_total         DECIMAL(9,2)                 DEFAULT 0,
  credit_total          DECIMAL(9,2)                 DEFAULT 0,
  terms_id              INT            NOT NULL,
  invoice_due_date      DATE           NOT NULL,
  payment_date          DATE,
  CONSTRAINT invoices_fk_vendors
    FOREIGN KEY (vendor_id)
    REFERENCES vendors (vendor_id),
  CONSTRAINT invoices_fk_terms
    FOREIGN KEY (terms_id)
    REFERENCES terms (terms_id)
);

ALTER TABLE invoices
ADD balance_due DECIMAL(9,2);

ALTER TABLE invoices
DROP COLUMN balance_due;

CREATE INDEX invoices_vendor_id_index
  ON invoices (vendor_id);

DROP INDEX invoices_vendor_id_index
    ON invocies;