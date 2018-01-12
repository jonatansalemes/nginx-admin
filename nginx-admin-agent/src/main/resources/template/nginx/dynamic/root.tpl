server {
        listen       80;
        server_name  localhost;
        location / {
            root   ${ nginxHome }/html;
            index  index.html;
        }
        
        location /status {
            stub_status on;
        }

        error_page  401               /401.html;
        error_page  403               /403.html;
        error_page  404               /404.html;
        error_page  500 501 502 503 504  /50x.html;
        location = /401.html {
            root  ${ nginxHome }/html;
        }
        location = /403.html {
            root  ${ nginxHome }/html;
        }
        location = /404.html {
            root  ${ nginxHome }/html;
        }
         location = /50x.html {
            root  ${ nginxHome }/html;
        }
}