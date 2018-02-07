create schema if not exists ${ database };
create table ${ database }.${ table } (
	id bigint(10) auto_increment not null, 
	name varchar(100) not null,
	version bigint(12) not null,
	primary key (id)
);
alter table ${ database }.${ table } add constraint ${ table }_uk1 unique(version);