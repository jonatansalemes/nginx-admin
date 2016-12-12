server {
        listen       80;
        server_name  localhost;

        location / {
            root   ${ nginx.settings }/html;
            index  index.html;
        }

        error_page  404               /404.html;
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root  ${ nginx.settings }/html;
        }
        location = /404.html {
            root  ${ nginx.settings }/html;
        }
}