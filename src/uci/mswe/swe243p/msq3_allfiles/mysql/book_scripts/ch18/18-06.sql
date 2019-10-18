-- create the user joel@localhost that was renamed and deleted in 18-04
CREATE USER IF NOT EXISTS joel@localhost IDENTIFIED BY 'sesame';

GRANT ALL 
ON *.* TO jim
WITH GRANT OPTiON;

GRANT SELECT, INSERT, UPDATE
ON ap.* TO joel@localhost;

GRANT SELECT, INSERT, UPDATE
ON ap.vendors TO joel@localhost;

GRANT SELECT (vendor_name, vendor_state, vendor_zip_code), 
      UPDATE (vendor_address1)
ON ap.vendors TO joel@localhost;

GRANT SELECT, INSERT, UPDATE, DELETE
ON vendors TO ap_user@localhost;

GRANT USAGE
ON *.* TO anne@localhost
WITH GRANT OPTION;