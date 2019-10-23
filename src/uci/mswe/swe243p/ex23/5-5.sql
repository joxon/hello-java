-- with iid as (
--   select
--     invoice_id
--   from
--     invoices
--   where
--     vendor_id = 32
--     and invoice_date = '2014-08-01'
--     and terms_id = 2
-- )
insert into
  invoice_line_items
values
  (115, 1, 160, 180.23, 'Hard drive'),
  (115, 2, 527, 254.35, 'Exchange Server update');