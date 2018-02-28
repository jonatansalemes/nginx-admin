alter table strategy add description varchar(400) not null default 'Strategy description';
alter table strategy add directive varchar(100);

update strategy set directive='ip_hash',name='Ip Hashing',description = 'This balancing algorithm distributes requests to different servers based on the clients IP address. The first three octets are used as a key to decide on the server to handle the request. The result is that clients tend to be served by the same server each time, which can assist in session consistency.' where name = 'ip_hash';
update strategy set directive=null,name='Round Robin',description = 'The default load balancing algorithm that is used if no other balancing directives are present. Each server defined in the upstream context is passed requests sequentially in turn.' where name = 'round-robin';
update strategy set directive='least_conn',name='Least Connected',description = 'Specifies that new connections should always be given to the backend that has the least number of active connections. This can be especially useful in situations where connections to the backend may persist for some time.' where name = 'least-connected';

alter table strategy add constraint strategy_uk2 unique(directive);
