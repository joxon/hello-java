use ex;
-- @block create members
-- @conn ex
drop table if exists members;
create table members (
  member_id int primary key,
  first_name varchar(40),
  last_name varchar(40),
  address varchar(40),
  city varchar(40),
  state varchar(40),
  phone varchar(40)
);
-- @block create committees
-- @conn ex
drop table if exists committees;
create table committees (
  committee_id int primary key,
  committee_name varchar(40)
);
-- @block create members_committees
-- @conn ex
drop table if exists members_committees;
create table members_committees (
  member_id int,
  committee_id int,
  constraint fk_member_id foreign key (member_id) references members (member_id),
  constraint fk_committee_id foreign key (committee_id) references committees (committee_id)
);