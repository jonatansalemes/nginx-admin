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
insert into admin.virtual_host_location (path,id_upstream,id_virtual_host) select '/',id_upstream,id from admin.virtual_host;
alter table admin.virtual_host DROP COLUMN id_upstream;

create table admin.virtual_host_alias (
	id bigint(10) auto_increment not null, 
	alias varchar(100) not null,
	id_virtual_host bigint(10) not null,
	primary key (id)
);
alter table admin.virtual_host_alias add constraint virtual_host_alias_uk1 unique(id_virtual_host,alias);
alter table admin.virtual_host_alias add constraint virtual_host_alias_fk1 foreign key(id_virtual_host) references admin.virtual_host(id);
insert into admin.virtual_host_alias (alias,id_virtual_host) select domain,id from admin.virtual_host;
alter table admin.virtual_host DROP COLUMN domain;

update admin.configuration set value = '3' where variable = 'DB_VERSION';