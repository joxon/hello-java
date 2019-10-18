CREATE TABLE product_descriptions
(
  product_id            INT           PRIMARY KEY,
  product_description   VARCHAR(200)
)
ENGINE = MyISAM;

ALTER TABLE product_descriptions ENGINE = InnoDB;

SET SESSION default_storage_engine = InnoDB;