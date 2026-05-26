CREATE DATABASE productos_sql;
USE productos_sql;

CREATE TABLE IF NOT EXIST PRODUCTO(
    id int primary key,
    nombre varchar(100) not null,
    precio bouble not null,
    cantidad int not null,
    categoria int not null,
    tipo varchar(20) not null,
    fecha_caducidad  date null,
    es_perecedero Boolean,
    peso double,
    marca varchar(10),
    garantia_meses INT,
    talla VARCHAR(10),
    color VARCHAR(30),
    material VARCHAR(20)
);