alter table admin.ssl_certificate add id_resource_identifier_certificate bigint(10) not null;
alter table admin.ssl_certificate add id_resource_identifier_certificate_private_key bigint(10) not null;
alter table admin.ssl_certificate add constraint ssl_certificate_fk1 foreign key(id_resource_identifier_certificate) references admin.resource_identifier(id);
alter table admin.ssl_certificate add constraint ssl_certificate_fk2 foreign key(id_resource_identifier_certificate_private_key) references admin.resource_identifier(id);

insert into admin.resource_identifier (hash) select certificate from admin.ssl_certificate;
insert into admin.resource_identifier (hash) select certificate_private_key from admin.ssl_certificate;
update admin.ssl_certificate set id_resource_identifier_certificate = (select id from admin.resource_identifier where hash = certificate);
update admin.ssl_certificate set id_resource_identifier_certificate_private_key = (select id from admin.resource_identifier where hash = certificate_private_key);
alter table admin.ssl_certificate drop certificate;
alter table admin.ssl_certificate drop certificate_private_key;

create table admin.access_log (
	id bigint(10) auto_increment not null, 
	date_time datetime not null,
	remote_addr varchar(100) not null,
	host varchar(100) not null,
	scheme varchar(100) not null,
	remote_user varchar(100) not null,
	body_bytes_sent bigint(10) not null,
	bytes_sent bigint(10) not null,
	connection bigint(10) not null,
	connection_request bigint(100) not null,
	msec decimal(13,3) not null,
	request_length bigint(10) not null,
	request_time decimal(10,2) not null,
	status int(3) not null,
	request varchar(100) not null,
	request_method varchar(100) not null,
	http_referrer varchar(100) not null,
	http_user_agent varchar(100) not null,
	http_x_forwarded_for varchar(100) not null,
	primary key (id)
);
insert into admin.configuration (variable,value) values ('URL_BASE','http://localhost:3000');
update admin.configuration set value = '4' where variable = 'DB_VERSION';