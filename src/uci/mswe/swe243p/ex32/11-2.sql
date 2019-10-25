use ex;
-- @block create members
-- @conn ex
drop table if exists members_committees;
drop table if exists committees;
drop table if exists members;
create table members (
  member_id int primary key auto_increment,
  first_name varchar(40) not null,
  last_name varchar(40) not null,
  address varchar(40),
  city varchar(40),
  state varchar(40),
  phone varchar(40)
);
-- @block create committees
-- @conn ex
create table committees (
  committee_id int primary key auto_increment,
  committee_name varchar(40) not null
);
-- @block create members_committees
-- @conn ex
create table members_committees (
  member_id int not null,
  committee_id int not null,
  primary key (member_id, committee_id),
  constraint fk_member_id foreign key (member_id) references members (member_id),
  constraint fk_committee_id foreign key (committee_id) references committees (committee_id)
);