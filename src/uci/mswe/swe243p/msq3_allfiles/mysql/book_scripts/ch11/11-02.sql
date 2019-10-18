USE ex;

CREATE TABLE vendors
(
  vendor_id     INT,
  vendor_name   VARCHAR(50)
);

CREATE TABLE vendors
(
  vendor_id     INT            NOT NULL    UNIQUE AUTO_INCREMENT,
  vendor_name   VARCHAR(50)    NOT NULL    UNIQUE
);

CREATE TABLE invoices
(
  invoice_id       INT            NOT NULL    UNIQUE,
  vendor_id        INT            NOT NULL,
  invoice_number   VARCHAR(50)    NOT NULL,
  invoice_date     DATE,
  invoice_total    DECIMAL(9,2)   NOT NULL,
  payment_total    DECIMAL(9,2)               DEFAULT 0
)