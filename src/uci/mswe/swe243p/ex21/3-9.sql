select
  concat(
    vendor_contact_last_name,
    ', ',
    vendor_contact_first_name
  ) as full_name
from
  vendors
where
  vendor_contact_last_name regexp '^[ABCE]'
order by
  vendor_contact_last_name asc,
  vendor_contact_first_name asc;