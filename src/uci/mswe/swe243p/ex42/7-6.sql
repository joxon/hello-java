select
  vendor_name,
  vendor_city,
  vendor_state
from
  vendors v1
where
  1 = (
    select
      count(1)
    from
      vendors v2
    where
      v2.vendor_city = v1.vendor_city
      and v2.vendor_state = v1.vendor_state
  )
order by
  vendor_state,
  vendor_city;