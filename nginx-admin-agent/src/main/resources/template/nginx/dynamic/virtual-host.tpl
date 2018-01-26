<#if https >
server {

       listen               80;
       
       server_name <#list aliases as alias> ${ alias } </#list>;

       location / {
            return 301 https://$server_name$request_uri;
       }
       
}
</#if>

server {
	<#if https>
		listen               443 ssl;
		ssl_certificate      ${ settings }/ssl/${ certificateUuid }.ssl;
		ssl_certificate_key  ${ settings }/ssl/${ certificatePrivateKeyUuid }.ssl;
		ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers "EECDH+AESGCM:EDH+AESGCM:ECDHE-RSA-AES128-GCM-SHA256:AES256+EECDH:DHE-RSA-AES128-GCM-SHA256:AES256+EDH:ECDHE-RSA-AES256-GCM-SHA384:DHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-SHA384:ECDHE-RSA-AES128-SHA256:ECDHE-RSA-AES256-SHA:ECDHE-RSA-AES128-SHA:DHE-RSA-AES256-SHA256:DHE-RSA-AES128-SHA256:DHE-RSA-AES256-SHA:DHE-RSA-AES128-SHA:ECDHE-RSA-DES-CBC3-SHA:EDH-RSA-DES-CBC3-SHA:AES256-GCM-SHA384:AES128-GCM-SHA256:AES256-SHA256:AES128-SHA256:AES256-SHA:AES128-SHA:DES-CBC3-SHA:HIGH:!aNULL:!eNULL:!EXPORT:!DES:!MD5:!PSK:!RC4";
        ssl_prefer_server_ciphers on;
        ssl_session_cache shared:SSL:10m;
        ssl_dhparam  ${ settings }/ssl/dhparam.pem;
	<#else>
	 	listen               80;
	</#if>
          
       	server_name <#list aliases as alias> ${ alias } </#list>;
        
    <#list locations as location>
    	
    	location ${ location.path } {
      		proxy_pass  http://${ location.upstream };
    	}
    	
    </#list>
}