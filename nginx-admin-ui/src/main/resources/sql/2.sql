alter table admin.nginx ALTER COLUMN home RENAME TO settings;
alter table admin.nginx add gzip int(1) not null;
alter table admin.nginx add maxPostSize int(4) not null;
update admin.nginx set gzip = 1,maxPostSize= 100;
update admin.configuration set value = '2' where variable = 'DB_VERSION';