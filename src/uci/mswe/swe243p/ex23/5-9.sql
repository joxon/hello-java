-- Delete line items first
delete from
  invoice_line_items
where
  invoice_id in (
    select
      max(invoice_id)
    from
      invoices
  );
-- ERROR 1093 (HY000): You can't specify target table 'invoices' for update in FROM clause
delete from
  invoices
where
  invoice_id in (
    select
      max(invoice_id)
    from
      invoices
  );
-- ✔, need to add a outer query as temp table
delete from
  invoices
where
  invoice_id in (
    select
      *
    from
      (
        select
          max(invoice_id)
        from
          invoices
      ) as max_id
  );