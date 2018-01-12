user  nginx;
worker_processes  1;

error_log  ${ nginxHome }/log/error.log warn;
pid        ${ nginxHome }/nginx.pid;

events {
    worker_connections  1024;
}


http {

	server_names_hash_bucket_size 		64;
    server_names_hash_max_size 			1024;
    
    include      				 		${ nginxHome }/mime.types;
    
    default_type  						application/octet-stream;
    
    proxy_set_header   					Host             	$host;
    proxy_set_header   					X-Real-IP        	$remote_addr;
    proxy_set_header 					X-Forwarded-Proto 	$scheme;

    log_format 							main 				'{"timestamp":"$time_iso8601","remote_addr":"$remote_addr","body_bytes_sent":"$body_bytes_sent","bytes_sent":"$bytes_sent","connection":"$connection","connection_requests":"$connection_requests","msec":"$msec","request":"$request","status":"$status","scheme":"$scheme","request_length":"$request_length","request_time":"$request_time","request_method":"$request_method","request_uri":"$request_uri","server_name":"$server_name","server_port":"$server_port","server_protocol":"$server_protocol","http_x_forwarded_for":"$http_x_forwarded_for","http_referrer":"$http_referer","http_user_agent":"$http_user_agent"}';
      					
    access_log  						${ nginxHome }/log/access.log  main;

    sendfile        					on;
    keepalive_timeout   				65;
    
    tcp_nopush	 						off;
    tcp_nodelay  						on;
    
    client_max_body_size 				${ maxPostSize?c }m;

	<#if gzip>
	    gzip 		on;
	    gzip_types 	application/json application/x-javascript application/javascript application/xml application/xml+rss text/plain text/css text/xml text/javascript image/svg+xml;
    </#if>
    
    include  	${ nginxHome }/upstream/*.conf;
	include  	${ nginxHome }/virtual-host/*.conf;
    
}