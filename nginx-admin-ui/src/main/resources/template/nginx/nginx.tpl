user  nginx;
worker_processes  1;

error_log  ${ nginx.settings }/log/error.log warn;
pid        ${ nginx.settings }/nginx.pid;

events {
    worker_connections  1024;
}


http {
    include       ${ nginx.settings }/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  ${ nginx.settings }/log/access.log  main;

    sendfile        on;
    keepalive_timeout  65;
    
    include  ${ nginx.settings }/upstream/*.conf;
    include  ${ nginx.settings }/virtual-domain/*.conf;
}