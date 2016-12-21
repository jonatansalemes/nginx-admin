server {
        listen       80;
        server_name  localhost;

        location / {
            root   ${ nginx.settings }/html;
            index  index.html;
        }

        error_page  401               /401.html;
        error_page  403               /403.html;
        error_page  404               /404.html;
        error_page  500 501 502 503 504  /50x.html;
        location = /401.html {
            root  ${ nginx.settings }/html;
        }
        location = /403.html {
            root  ${ nginx.settings }/html;
        }
        location = /404.html {
            root  ${ nginx.settings }/html;
        }
         location = /50x.html {
            root  ${ nginx.settings }/html;
        }
}