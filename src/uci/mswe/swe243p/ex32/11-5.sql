alter table
  committees
modify
  committee_name varchar(40) not null unique;
insert into
  committees
values
  (default, 'Com Alpha');
-- ER_DUP_ENTRY: Duplicate entry 'Com Alpha' for key 'committee_name'