CREATE VIEW vendors_sw AS
SELECT *
FROM vendors
WHERE vendor_state IN ('CA','AZ','NV','NM');

CREATE OR REPLACE VIEW vendors_sw AS
SELECT *
FROM vendors
WHERE vendor_state IN ('CA','AZ','NV','NM','UT','CO');

DROP VIEW vendors_sw;