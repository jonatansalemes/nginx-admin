insert into admin.configuration (variable,value) values ('URL_BASE','http://localhost:3000');
update admin.configuration set value = '4' where variable = 'DB_VERSION';