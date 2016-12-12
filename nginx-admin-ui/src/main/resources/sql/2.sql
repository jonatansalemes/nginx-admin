alter table admin.nginx ALTER COLUMN home RENAME TO settings;
update admin.configuration set value = '2' where variable = 'DB_VERSION';