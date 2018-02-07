create sequence migrate.user_sq minvalue 1 start with 1 increment by 1;
create table migrate.user (
	id bigint(10) not null, 
	login varchar(100) not null,
	email varchar(100) not null,
	password varchar(100) not null,
	password_force_change int(1) not null,
	primary key (id)
);
alter table migrate.user add constraint user_uk1 unique(login);
alter table migrate.user add constraint user_uk2 unique(email);

insert into migrate.user (id,login,email,password,password_force_change) values (migrate.user_sq.nextval,'admin','admin@localhost.com','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',1);