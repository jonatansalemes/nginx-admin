#!/bin/bash
function file_exists(){
    if [ -e $1 ]; then
        return $true
    else
        return $false
    fi
}

NGINX_ADMIN_HOME=/opt/nginx-admin
NGINX_ADMIN_BIN=$NGINX_ADMIN_HOME/bin
NGINX_ADMIN_VERSION=1.0.3

if ! file_exists "/etc/init.d/nginx-admin" ; then 
	echo "Nginx admin seems not be installed yet"
	exit
fi

service nginx-admin stop

if ! file_exists "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then 
	wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar -O $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
fi

chown -R nginx-admin:nginx-admin $NGINX_ADMIN_BIN/*.jar
sed -i 's/NGINX_ADMIN_VERSION=\(.*\)/NGINX_ADMIN_VERSION=$NGINX_ADMIN_VERSION/g' $NGINX_ADMIN_HOME/conf/nginx-admin.conf

service nginx-admin start

echo "Nginx admin was successfully updated to latest version $NGINX_ADMIN_VERSION."