update
  invoices
set
  terms_id = 2
where
  vendor_id in (
    select
      vendor_id
    from
      vendors
    where
      default_terms_id = 2
  );