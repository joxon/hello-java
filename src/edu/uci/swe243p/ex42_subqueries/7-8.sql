-- ! https://docs.oracle.com/cd/B10501_01/server.920/a96540/queries8.htm
-- A subquery in the FROM clause of a SELECT statement is also called an inline view.
-- A subquery in the WHERE clause of a SELECT statement is also called a nested subquery.
-- Oracle performs a correlated subquery when the subquery references
-- a column from a table referred to in the parent statement.
-- A correlated subquery is evaluated once for each row processed by the parent statement.
-- The parent statement can be a SELECT, UPDATE, or DELETE statement.
select
  vendor_name,
  invoice_number,
  invoice_date,
  invoice_total
from
  vendors natural
  join (
    select
      vendor_id,
      -- ! important for natural join
      invoice_id,
      invoice_number,
      min(invoice_date) as invoice_date,
      invoice_total
    from
      invoices
    group by
      vendor_id
  ) as oldest_invoices
order by
  vendor_name;