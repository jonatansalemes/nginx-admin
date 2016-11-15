CREATE SCHEMA admin;
create table admin.user (
	id bigint auto_increment, 
	login varchar(100),
	password varchar(100),
	primary key (id)
);
insert into admin.user (login,password) values ('admin',HASH('SHA256', STRINGTOUTF8('admin'), 1));

create table admin.configuration (
	id bigint auto_increment, 
	variable varchar(100),
	value varchar(100),
	primary key (id)
);
insert into admin.configuration (variable,value) values ('VERSION','1');
commit;