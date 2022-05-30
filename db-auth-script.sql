create database dbauth;

create table tb_adm (
	adm_id serial not null primary key,
  	adm_name varchar(100) not null,
    adm_email varchar(100) not null unique,
    adm_password text not null
);