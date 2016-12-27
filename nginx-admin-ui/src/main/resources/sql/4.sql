create table admin.application (
	id bigint(10) auto_increment not null, 
	db_version int(2) not null,
	url_base varchar(100) not null,
	primary key (id)
);
drop table admin.configuration;
update admin.application set db_version = '4',url_base = 'http://localhost:3000';