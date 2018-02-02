create table ${ schema }.${ tableName } (
	id bigint(10) auto_increment not null, 
	name varchar(100) not null,
	version bigint(12) not null,
	primary key (id)
);
alter table ${ schema }.${ tableName } add constraint user_uk1 unique(version);