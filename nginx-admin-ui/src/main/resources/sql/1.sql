CREATE SCHEMA admin;
create table admin.user (
	id bigint auto_increment not null, 
	login varchar(100) not null,
	password varchar(100) not null,
	password_force_change int(1) not null,
	primary key (id)
);
alter table admin.user add constraint user_uk1 unique(login);
insert into admin.user (login,password,password_force_change) values ('admin@localhost.com',HASH('SHA256', STRINGTOUTF8('password'),1),1);

create table admin.configuration (
	id bigint auto_increment not null, 
	variable varchar(100) not null,
	value varchar(100),
	primary key (id)
);
alter table admin.configuration add constraint configuration_uk1 unique(variable);
insert into admin.configuration (variable,value) values ('VERSION','1');
insert into admin.configuration (variable,value) values ('NGINX_USER','nginx');
insert into admin.configuration (variable,value) values ('NGINX_GROUP','nginx');
insert into admin.configuration (variable,value) values ('NGINX_HOME','D:\softwares\nginx-1.11.6');
insert into admin.configuration (variable,value) values ('NGINX_BIN','D:\softwares\nginx-1.11.6\nginx.exe');
insert into admin.configuration (variable,value) values ('NGINX_CONF','D:\softwares\nginx-1.11.6\conf\nginx.conf');
insert into admin.configuration (variable,value) values ('NGINX_ERROR_LOG','D:\softwares\nginx-1.11.6\logs\error.log');
insert into admin.configuration (variable,value) values ('NGINX_ACCESS_LOG','D:\softwares\nginx-1.11.6\logs\access.log');

insert into admin.configuration (variable,value) values ('SMTP_HOST','localhost');
insert into admin.configuration (variable,value) values ('SMTP_PORT','25');
insert into admin.configuration (variable,value) values ('SMTP_TLS','false');
insert into admin.configuration (variable,value) values ('SMTP_AUTHENTICATE','false');
insert into admin.configuration (variable,value) values ('SMTP_USERNAME','');
insert into admin.configuration (variable,value) values ('SMTP_PASSWORD','');
insert into admin.configuration (variable,value) values ('SMTP_FROM','nginx.admin@yourdomain.com');


commit;