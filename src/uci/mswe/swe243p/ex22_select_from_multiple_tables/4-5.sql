select
  a.vendor_id,
  a.vendor_name,
  concat(
    a.vendor_contact_first_name,
    ' ',
    a.vendor_contact_last_name
  ) as contact_name
from
  vendors a
  join vendors b
where
  a.vendor_id != b.vendor_id
  and a.vendor_contact_last_name = b.vendor_contact_last_name
order by
  a.vendor_contact_last_name;