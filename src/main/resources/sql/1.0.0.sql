CREATE SCHEMA admin;
create table admin.user (
	id bigint auto_increment, 
	login varchar(100),
	password varchar(255),
	primary key (id)
);
insert into admin.user (login,password) values ('admin',HASH('SHA256', STRINGTOUTF8('admin'), 1));
commit;