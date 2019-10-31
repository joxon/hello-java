-- ! https://www.geeksforgeeks.org/cte-in-sql/
-- The Common Table Expressions (CTE) were introduced into standard SQL
-- in order to simplify various classes of SQL Queries for which a derived table was just unsuitable.
with max_unpaid_for_each_vendor(vendor_id, max_unpaid) as (
  select
    vendor_id,
    max(invoice_total) as max_unpaid
  from
    invoices
  where
    invoice_total - payment_total - credit_total > 0
  group by
    vendor_id
)
select
  sum(max_unpaid)
from
  max_unpaid_for_each_vendor;