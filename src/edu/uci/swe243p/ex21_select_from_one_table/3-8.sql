select
  vendor_name,
  vendor_contact_last_name,
  vendor_contact_first_name
from
  Vendors
order by
  vendor_contact_last_name asc,
  vendor_contact_first_name asc;