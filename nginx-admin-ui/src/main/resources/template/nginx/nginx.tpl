user  nginx;
worker_processes  1;

error_log  ${ nginx.home }/settings/log/error.log warn;
pid        ${ nginx.home }/settings/process/nginx.pid;

events {
    worker_connections  1024;
}


http {
    include       ${ nginx.home }/settings/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  ${ nginx.home }/settings/log/access.log  main;

    sendfile        on;
    keepalive_timeout  65;
    
    include  ${ nginx.home }/settings/upstream/*.conf;
    include  ${ nginx.home }/settings/virtual-domain/*.conf;
}