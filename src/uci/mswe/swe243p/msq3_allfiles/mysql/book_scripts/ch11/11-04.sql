USE ex;

CREATE TABLE invoices
(
  invoice_id       INT            PRIMARY KEY,
  vendor_id        INT            REFERENCES vendors (vendor_id),
  invoice_number   VARCHAR(50)    NOT NULL    UNIQUE
);

CREATE TABLE invoices
(
  invoice_id       INT           PRIMARY KEY,
  vendor_id        INT           NOT NULL,
  invoice_number   VARCHAR(50)   NOT NULL    UNIQUE,
  CONSTRAINT invoices_fk_vendors
    FOREIGN KEY (vendor_id) REFERENCES vendors (vendor_id)
)
