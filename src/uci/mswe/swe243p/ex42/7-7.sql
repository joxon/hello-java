-- ! https://www.geeksforgeeks.org/sql-correlated-subqueries/
-- A correlated subquery is evaluated once for each row processed by the parent statement.
select
  vendor_name,
  invoice_number,
  invoice_date,
  invoice_total
from
  vendors natural
  join invoices
where
  (invoice_id, invoice_date) in (
    select
      invoice_id,
      min(invoice_date)
    from
      invoices
    group by
      vendor_id
  )
order by
  vendor_name;