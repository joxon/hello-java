select
  *
from
  vendors v
  inner join invoices i on v.vendor_id = i.vendor_id;