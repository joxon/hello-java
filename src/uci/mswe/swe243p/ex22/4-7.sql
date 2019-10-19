(
  select
    vendor_name,
    'CA' as vendor_state
  from
    vendors
  where
    vendor_state = 'CA'
)
union
  (
    select
      vendor_name,
      'Outside CA.' as vendor_state
    from
      vendors
    where
      vendor_state <> 'CA'
  )
order by
  vendor_name