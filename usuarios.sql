drop table if exists usuarios cascade; -- se añade cascade porque es referenciada

create table usuarios (
correo varchar(40) primary key,
passwd varchar(30));
