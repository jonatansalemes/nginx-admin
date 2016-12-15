server {
        listen 80;
        server_name agenda.jslsolucoes.com;
        
        location / {
            return 301 https://$server_name$request_uri;
        }
}

server {
        listen               443 ssl;
        ssl_certificate      /etc/nginx/ssl/jslsolucoes.crt;
        ssl_certificate_key  /etc/nginx/ssl/jslsolucoes.key;
        server_name agenda.jslsolucoes.com;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers "EECDH+AESGCM:EDH+AESGCM:ECDHE-RSA-AES128-GCM-SHA256:AES256+EECDH:DHE-RSA-AES128-GCM-SHA256:AES256+EDH:ECDHE-RSA-AES256-GCM-SHA384:DHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-SHA384:ECDHE-RSA-AES128-SHA256:ECDHE-RSA-AES256-SHA:ECDHE-RSA-AES128-SHA:DHE-RSA-AES256-SHA256:DHE-RSA-AES128-SHA256:DHE-RSA-AES256-SHA:DHE-RSA-AES128-SHA:ECDHE-RSA-DES-CBC3-SHA:EDH-RSA-DES-CBC3-SHA:AES256-GCM-SHA384:AES128-GCM-SHA256:AES256-SHA256:AES128-SHA256:AES256-SHA:AES128-SHA:DES-CBC3-SHA:HIGH:!aNULL:!eNULL:!EXPORT:!DES:!MD5:!PSK:!RC4";
        ssl_prefer_server_ciphers on;
        ssl_session_cache shared:SSL:10m;
        ssl_dhparam /etc/nginx/ssl/dhparam.pem;

        location ~* \.(js|css|png|jpg|jpeg|gif|ico)$ {
             proxy_pass  http://wildfly;
        }

        location / {
          limit_req   zone=blitz burst=5 nodelay;
          proxy_pass  http://wildfly;
        }
}
