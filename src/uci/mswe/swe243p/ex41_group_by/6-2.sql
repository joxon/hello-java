-- inner join
select
  vendor_name,
  sum(payment_total) as sum_payment_total
from
  vendors v,
  invoices i
where
  v.vendor_id = i.vendor_id
group by
  i.vendor_id
order by
  sum_payment_total desc;
-- natural join
select
  vendor_name,
  sum(payment_total) as sum_payment_total
from
  vendors natural
  join invoices
group by
  vendor_id
order by
  sum_payment_total desc;