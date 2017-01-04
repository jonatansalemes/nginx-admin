alter table admin.access_log alter column remote_addr varchar(15) not null;
alter table admin.access_log alter column scheme varchar(5) not null;
alter table admin.access_log alter column request varchar(2084) not null;
alter table admin.access_log alter column request_uri varchar(2084) not null;
alter table admin.access_log alter column http_referrer varchar(2084) not null;
alter table admin.access_log alter column request_method varchar(10) not null;
alter table admin.access_log alter column http_user_agent varchar(255) not null;
alter table admin.access_log alter column server_protocol varchar(10) not null;
update admin.configuration set value = '5' where variable = 'DB_VERSION';