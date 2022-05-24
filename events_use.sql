drop table if exists events_use cascade; -- se añade cascade porque es referenciada

create table events_use (
event_id integer,
email varchar(40),
date1 timestamp,
advice_date timestamp,
name varchar(40),
color varchar(40),
note varchar(255),
primary key (event_id,email),
foreign key (email) references users(email) on update cascade on delete cascade);
