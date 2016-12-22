alter table admin.virtual_domain RENAME TO virtual_host;
alter table admin.nginx ALTER COLUMN home RENAME TO settings;
alter table admin.nginx add gzip int(1) not null;
alter table admin.nginx add max_post_size int(4) not null;
update admin.nginx set gzip = 1,max_post_size= 100;
update admin.configuration set value = '2' where variable = 'DB_VERSION';