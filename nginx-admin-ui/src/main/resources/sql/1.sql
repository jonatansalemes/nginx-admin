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
insert into admin.configuration (variable,value) values ('NGINX_USER','nginx');
insert into admin.configuration (variable,value) values ('NGINX_GROUP','nginx');
insert into admin.configuration (variable,value) values ('NGINX_HOME','/etc/nginx');
insert into admin.configuration (variable,value) values ('NGINX_BIN','/usr/sbin/nginx');
insert into admin.configuration (variable,value) values ('NGINX_CONF','/etc/nginx/nginx.conf');
insert into admin.configuration (variable,value) values ('NGINX_ERROR_LOG','/var/log/nginx/error.log');
insert into admin.configuration (variable,value) values ('NGINX_ACCESS_LOG','/var/log/nginx/access.log');

insert into admin.configuration (variable,value) values ('SMTP_HOST','localhost');
insert into admin.configuration (variable,value) values ('SMTP_PORT','25');
insert into admin.configuration (variable,value) values ('SMTP_TLS','false');
insert into admin.configuration (variable,value) values ('SMTP_AUTHENTICATE','false');
insert into admin.configuration (variable,value) values ('SMTP_USERNAME','');
insert into admin.configuration (variable,value) values ('SMTP_PASSWORD','');
insert into admin.configuration (variable,value) values ('SMTP_FROM','nginx.admin@yourdomain.com');


commit;