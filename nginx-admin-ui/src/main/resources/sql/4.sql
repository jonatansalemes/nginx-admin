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