CREATE INDEX invoices_invoice_date_ix
  ON invoices (invoice_date);
  
CREATE INDEX invoices_vendor_id_invoice_number_ix
  ON invoices (vendor_id, invoice_number);
  
CREATE UNIQUE INDEX vendors_vendor_phone_ix
  ON vendors (vendor_phone);
  
CREATE INDEX invoices_invoice_total_ix
  ON invoices (invoice_total DESC);
  
DROP INDEX vendors_vendor_phone_ix ON vendors;