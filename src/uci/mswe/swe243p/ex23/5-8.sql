-- ❌
update
  (invoices as i)
  left join (vendors as v) on i.vendor_id = v.vendor_id
  and v.default_terms_id = 2
  and v.default_terms_id is not null
set
  terms_id = 2;
-- ✔
update
  invoices as i,
  (
    -- try to find invoices that have vendors with default_terms_id = 2
    select
      ii.invoice_id
    from
      (invoices as ii)
      left join (vendors as v) on ii.vendor_id = v.vendor_id
      and v.default_terms_id = 2
    where
      v.default_terms_id is not null
  ) as j
set
  terms_id = 2
where
  i.invoice_id = j.invoice_id;
-- ✔, safe mode disabling required
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