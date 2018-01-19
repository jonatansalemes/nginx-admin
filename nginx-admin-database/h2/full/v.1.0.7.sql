DROP SCHEMA IF EXISTS admin;

CREATE SCHEMA admin;
create table admin.user (
	id bigint(10) auto_increment not null, 
	login varchar(100) not null,
	password varchar(100) not null,
	password_force_change int(1) not null,
	primary key (id)
);
alter table admin.user add constraint user_uk1 unique(login);

create table admin.resource_identifier (
	id bigint(10) auto_increment not null, 
	hash varchar(100) not null,
	primary key (id)
);
alter table admin.resource_identifier add constraint resource_identifier_uk1 unique(hash);

create table admin.nginx (
	id bigint(10) auto_increment not null, 
	bin varchar(255) not null,
	home varchar(255) not null,
	ip varchar(255) not null,
	port int(5) not null,
	gzip int(1) not null,
	max_post_size int(4) not null,
	authorization_key varchar(255) not null,
	primary key (id)
);

create table admin.ssl_certificate (
	id bigint(10) auto_increment not null, 
	common_name varchar(100) not null,
	id_resource_identifier_certificate bigint(10),
	id_resource_identifier_certificate_private_key bigint(10),
	primary key (id)
);
alter table admin.ssl_certificate add constraint ssl_certificate_uk1 unique(common_name);
alter table admin.ssl_certificate add constraint ssl_certificate_fk1 foreign key(id_resource_identifier_certificate) references admin.resource_identifier(id);
alter table admin.ssl_certificate add constraint ssl_certificate_fk2 foreign key(id_resource_identifier_certificate_private_key) references admin.resource_identifier(id);


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

create table admin.virtual_host (
	id bigint(10) auto_increment not null, 
	https int(1) not null,
	id_ssl_certificate bigint(10),
	id_resource_identifier bigint(10) not null,
	primary key (id)
);
alter table admin.virtual_host add constraint virtual_host_fk1 foreign key(id_ssl_certificate) references admin.ssl_certificate(id);
alter table admin.virtual_host add constraint virtual_host_fk2 foreign key(id_resource_identifier) references admin.resource_identifier(id);


create table admin.virtual_host_location (
	id bigint(10) auto_increment not null, 
	id_upstream bigint(10) not null,
	path varchar(100) not null,
	id_virtual_host bigint(10) not null,
	primary key (id)
);
alter table admin.virtual_host_location add constraint virtual_host_location_uk1 unique(id_virtual_host,path);
alter table admin.virtual_host_location add constraint virtual_host_location_fk1 foreign key(id_upstream) references admin.upstream(id);
alter table admin.virtual_host_location add constraint virtual_host_location_fk2 foreign key(id_virtual_host) references admin.virtual_host(id);


create table admin.virtual_host_alias (
	id bigint(10) auto_increment not null, 
	alias varchar(100) not null,
	id_virtual_host bigint(10) not null,
	primary key (id)
);
alter table admin.virtual_host_alias add constraint virtual_host_alias_uk1 unique(id_virtual_host,alias);
alter table admin.virtual_host_alias add constraint virtual_host_alias_fk1 foreign key(id_virtual_host) references admin.virtual_host(id);

create table admin.access_log (
	id bigint(10) auto_increment not null,
	date_time datetime not null,
	remote_addr varchar(15) not null,
	body_bytes_sent bigint(10) not null,
	bytes_sent bigint(10) not null,
	connection bigint(10) not null,
	connection_request bigint(100) not null,
	msec decimal(13,3) not null,
	request varchar(2084) not null,
	status int(3) not null,
	scheme varchar(5) not null,
	request_length bigint(10) not null,
	request_time decimal(10,2) not null,
	request_method varchar(10) not null,
	request_uri varchar(2084) not null,
	server_name varchar(100) not null,
    server_port int(5) not null,
    server_protocol varchar(10) not null,
	http_referrer varchar(2084) not null,
	http_user_agent varchar(255) not null,
	http_x_forwarded_for varchar(100) not null,
	primary key (id)
);

commit;