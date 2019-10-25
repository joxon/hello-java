insert into
  members
values
  (
    default,
    'Alice',
    'Alpha',
    '3900 Parkview Ln',
    'Irvine',
    'CA',
    '100-200-3000'
  ),(
    default,
    'Bob',
    'Beta',
    '3901 Parkview Ln',
    'Irvine',
    'CA',
    '100-200-3001'
  );
insert into
  committees
values
  (default, 'Com Alpha'),
  (default, 'Com Beta');
insert into
  members_committees
values
  (1, 2),
  (2, 1),
  (2, 2);
select
  committee_name,
  last_name,
  first_name
from
  members natural
  join members_committees natural
  join committees
order by
  committee_name,
  last_name,
  first_name;