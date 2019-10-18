CREATE OR REPLACE VIEW vendor_address
AS
SELECT vendor_id, vendor_address1, vendor_address2, vendor_city, vendor_state, vendor_zip_code
FROM vendors
