USE ex;

CREATE TABLE vendors
(
  vendor_id     INT            PRIMARY KEY   AUTO_INCREMENT,
  vendor_name   VARCHAR(50)    NOT NULL      UNIQUE
);

CREATE TABLE vendors
(
  vendor_id     INT            AUTO_INCREMENT,
  vendor_name   VARCHAR(50)    NOT NULL,
  CONSTRAINT vendors_pk PRIMARY KEY (vendor_id),
  CONSTRAINT vendor_name_uq UNIQUE (vendor_name)
);

CREATE TABLE invoice_line_items
(
  invoice_id              INT           NOT NULL,
  invoice_sequence        INT           NOT NULL,
  line_item_description   VARCHAR(100)  NOT NULL,
  CONSTRAINT line_items_pk PRIMARY KEY (invoice_id, invoice_sequence)
)