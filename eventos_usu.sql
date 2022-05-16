drop table if exists eventos_usu cascade; -- se añade cascade porque es referenciada

create table eventos_usu (
id_evento integer,
correo varchar(40) references usuarios,
fecha timestamp,
fecha_aviso timestamp,
nombre varchar(40),
primary key (id_evento,correo));
