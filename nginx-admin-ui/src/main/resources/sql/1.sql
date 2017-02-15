DROP SCHEMA IF EXISTS admin;

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

create table admin.resource_identifier (
	id bigint(10) auto_increment not null, 
	hash varchar(100) not null,
	primary key (id)
);
alter table admin.resource_identifier add constraint resource_identifier_uk1 unique(hash);

create table admin.configuration (
	id bigint(10) auto_increment not null, 
	variable varchar(100) not null,
	value varchar(100) not null,
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
alter table admin.ssl_certificate add constraint ssl_certificate_uk1 unique(common_name);

create table admin.strategy (
	id bigint(10) auto_increment not null, 
	name varchar(100) not null,
	primary key (id)
);
alter table admin.strategy add constraint strategy_uk1 unique(name);
insert into admin.strategy (name) values ('ip_hash');
insert into admin.strategy (name) values ('round-robin');
insert into admin.strategy (name) values ('least-connected');

create table admin.server (
	id bigint(10) auto_increment not null, 
	ip varchar(100) not null,
	primary key (id)
);
alter table admin.server add constraint server_uk1 unique(ip);

create table admin.upstream (
	id bigint(10) auto_increment not null, 
	name varchar(100) not null,
	id_strategy bigint(10) not null, 
	id_resource_identifier bigint(10) not null, 
	primary key (id)
);
alter table admin.upstream add constraint upstream_uk1 unique(name);
alter table admin.upstream add constraint upstream_fk1 foreign key(id_strategy) references admin.strategy(id);
alter table admin.upstream add constraint upstream_fk2 foreign key(id_resource_identifier) references admin.resource_identifier(id);

create table admin.upstream_server (
	id bigint(10) auto_increment not null, 
	id_server bigint(10) not null, 
	id_upstream bigint(10) not null, 
	port int(5) not null,
	primary key (id)
);
alter table admin.upstream_server add constraint upstream_server_uk1 unique(id_server,id_upstream,port);
alter table admin.upstream_server add constraint upstream_server_fk1 foreign key(id_server) references admin.server(id);
alter table admin.upstream_server add constraint upstream_server_fk2 foreign key(id_upstream) references admin.upstream(id);

create table admin.virtual_domain (
	id bigint(10) auto_increment not null, 
	https int(1) not null,
	id_ssl_certificate bigint(10), 
	id_upstream bigint(10) not null, 
	domain varchar(100) not null,
	id_resource_identifier bigint(10) not null,
	primary key (id)
);
alter table admin.virtual_domain add constraint virtual_domain_uk1 unique(domain);
alter table admin.virtual_domain add constraint virtual_domain_fk1 foreign key(id_ssl_certificate) references admin.ssl_certificate(id);
alter table admin.virtual_domain add constraint virtual_domain_fk2 foreign key(id_upstream) references admin.upstream(id);
alter table admin.virtual_domain add constraint virtual_domain_fk3 foreign key(id_resource_identifier) references admin.resource_identifier(id);


commit;