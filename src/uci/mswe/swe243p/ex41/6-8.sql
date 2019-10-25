-- ! https://mysqlserverteam.com/mysql-8-0-grouping-function/
-- The GROUPING function can be used in the above example
-- to differentiate NULLs produced by ROLLUP from
-- NULLs from the grouped data.
-- GROUPING function for a column returns a value of 1
-- when the NULL generated for that column is a result of ROLLUP operation.
-- Else it returns a value of 0.
select
  if(
    grouping(terms_id) = 1,
    'All terms',
    terms_id
  ) as terms_id,
  if(
    grouping(vendor_id) = 1,
    'All vendors',
    vendor_id
  ) as vendor_id,
  max(payment_date) as last_payment_date,
  sum(invoice_total - payment_total - credit_total) as sum_balance_due
from
  invoices
group by
  terms_id,
  vendor_id with rollup;