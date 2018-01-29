insert into admin.nginx (id,name,endpoint,authorization_key) values (1,'admin01','http://192.168.99.100:3000','fdoinsafodsoianoifd');

insert into admin.access_log(id_nginx,date_time,remote_addr,body_bytes_sent,bytes_sent,connection,connection_request,msec,request,status,scheme,request_length,request_time,request_method,
	request_uri,server_name,server_port,server_protocol,http_referrer,http_user_agent,http_x_forwarded_for) values (1,now(),'127.0.0.1',1,1,1,12,0.23,'GET /sd 200',200,'http',234,2,'GET','/sd','aaa',122,'http','-','chrome','-');