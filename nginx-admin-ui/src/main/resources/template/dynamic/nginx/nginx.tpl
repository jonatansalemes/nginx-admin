user  nginx;
worker_processes  1;

error_log  ${ nginx.settings }/log/error.log warn;
pid        ${ nginx.settings }/nginx.pid;

events {
    worker_connections  1024;
}


http {

	server_names_hash_bucket_size 		64;
    server_names_hash_max_size 			1024;
    
    include      				 		${ nginx.settings }/mime.types;
    
    default_type  						application/octet-stream;
    
    proxy_set_header   					Host             	$host;
    proxy_set_header   					X-Real-IP        	$remote_addr;
    proxy_set_header 					X-Forwarded-Proto 	$scheme;

    log_format 							main 				'{"timestamp":"$time_iso8601","remote_addr":"$remote_addr","host":"$host","scheme":"$scheme","remote_user":"$remote_user","body_bytes_sent":"$body_bytes_sent","bytes_sent":"$bytes_sent","connection":"$connection","connection_requests":"$connection_requests","msec": "$msec","request_length":"$request_length","request_time":"$request_time","status": "$status","request":"$request","request_method":"$request_method","http_x_forwarded_for":"$http_x_forwarded_for","http_referrer":"$http_referer","http_user_agent":"$http_user_agent"}';
      					
    access_log  						${ nginx.settings }/log/access.log  main;

    sendfile        					on;
    keepalive_timeout   				65;
    
    tcp_nopush	 						off;
    tcp_nodelay  						on;
    
    client_max_body_size 				${ nginx.maxPostSize?c }m;

	<#if nginx.gzip == 1>
	    gzip 		on;
	    gzip_types 	application/json application/x-javascript application/javascript application/xml application/xml+rss text/plain text/css text/xml text/javascript image/svg+xml;
    </#if>
    
    include  	${ nginx.settings }/upstream/*.conf;
	include  	${ nginx.settings }/virtual-host/*.conf;
    
}