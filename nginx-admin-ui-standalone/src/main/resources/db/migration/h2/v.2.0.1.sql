alter table strategy add description_key varchar(100) not null default 'strategy.default';
alter table strategy add directive varchar(100);

update strategy set directive='ip_hash',name='Ip Hashing',description_key = 'strategy.ip.hashing.description' where name = 'ip_hash';
update strategy set directive=null,name='Round Robin',description_key = 'strategy.round.robin.description' where name = 'round-robin';
update strategy set directive='least_conn',name='Least Connected',description_key = 'strategy.least.connected.description' where name = 'least-connected';

alter table strategy add constraint strategy_uk2 unique(directive);
