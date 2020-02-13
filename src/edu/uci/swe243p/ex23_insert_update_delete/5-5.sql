-- pk = invoice_id + invoice_sequence
insert into
  invoice_line_items (
    select
      max(invoice_id) as invoice_id,
      1,
      160,
      180.23,
      'Hard drive'
    from
      invoices
  );
--
insert into
  invoice_line_items (
    select
      max(invoice_id) as invoice_id,
      2,
      527,
      254.35,
      'Exchange Server update'
    from
      invoices
  );