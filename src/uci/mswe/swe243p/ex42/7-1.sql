-- original
select
  distinct vendor_name
from
  vendors
  join invoices on vendors.vendor_id = invoices.vendor_id
order by
  vendor_name;
-- without joining
select
  distinct vendor_name
from
  vendors
where
  vendor_id in (
    select
      vendor_id
    from
      invoices
  )
order by
  vendor_name;