drop table if exists users cascade; -- se añade cascade porque es referenciada

create table users (
email varchar(40) primary key,
passwd varchar(30),
session_id integer);
