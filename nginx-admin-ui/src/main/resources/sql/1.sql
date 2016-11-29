CREATE SCHEMA admin;
create table admin.user (
	id bigint(10) auto_increment not null, 
	login varchar(100) not null,
	password varchar(100) not null,
	password_force_change int(1) not null,
	admin int(1) not null,
	primary key (id)
);
alter table admin.user add constraint user_uk1 unique(login);

create table admin.configuration (
	id bigint(10) auto_increment not null, 
	variable varchar(100) not null,
	value varchar(100),
	primary key (id)
);
alter table admin.configuration add constraint configuration_uk1 unique(variable);
insert into admin.configuration (variable,value) values ('DB_VERSION','1');

create table admin.nginx (
	id bigint(10) auto_increment not null, 
	bin varchar(255) not null,
	home varchar(255) not null,
	primary key (id)
);

create table admin.smtp (
	id bigint(10) auto_increment not null, 
	host varchar(100) not null,
	username varchar(100),
	password varchar(100),
	from_address varchar(100) not null,
	port int(5) not null,
	tls int(1) not null,
	authenticate int(1) not null,
	primary key (id)
);

create table admin.ssl_certificate (
	id bigint(10) auto_increment not null, 
	common_name varchar(100) not null,
	certificate varchar(100) not null,
	certificate_private_key varchar(100) not null,
	primary key (id)
);

commit;