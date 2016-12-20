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

    log_format  main  					'$remote_addr - $remote_user [$time_local] "$request" '
                      					'$status $body_bytes_sent "$http_referer" '
                      					'"$http_user_agent" "$http_x_forwarded_for"';

    access_log  						${ nginx.settings }/log/access.log  main;

    sendfile        					on;
    keepalive_timeout   				65;
    
    tcp_nopush	 						off;
    tcp_nodelay  						on;
    
    client_max_body_size 				${ nginx.maxPostSize?c }m;

	<#if nginx.gzip == 1>
	    gzip 		on;
	    gzip_types 	application/json application/x-javascript application/javascript application/xml application/xml+rss text/html text/plain text/css text/xml text/javascript image/svg+xml;
    </#if>
    
    include  	${ nginx.settings }/upstream/*.conf;
	include  	${ nginx.settings }/virtual-domain/*.conf;
    
}