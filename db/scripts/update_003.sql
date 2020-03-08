drop table items;
create table items (
   id serial primary key,
   name varchar(100) not null,
   description varchar(100) not null,
   time bigint not null
);