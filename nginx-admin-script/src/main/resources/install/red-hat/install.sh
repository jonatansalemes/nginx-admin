#!/bin/bash 
function package_exists(){
    if rpm -q --quiet $1; then 
     	return $true
    else
        return $false
    fi
}

function file_exists(){
    if [ -e $1 ]; then
        return $true
    else
        return $false
    fi
}

NGINX_ADMIN_HOME=/opt/nginx-admin
NGINX_ADMIN_CONF=$NGINX_ADMIN_HOME/conf
NGINX_ADMIN_BIN=$NGINX_ADMIN_HOME/bin
NGINX_ADMIN_LOG=$NGINX_ADMIN_HOME/log
NGINX_ADMIN_VERSION=1.0.1

yum -y update

if ! package_exists initscripts ; then 
	echo "installing initscripts ..."
	yum -y install initscripts
fi

if ! package_exists wget ; then 
	echo "installing wget ..."
	yum -y install wget
fi 

if ! package_exists nginx ; then 
  echo "installing nginx ..."
  printf '[nginx]\nname=nginx repo\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/\ngpgcheck=0\nenabled=1\n' > /etc/yum.repos.d/nginx.repo
  yum -y install nginx
fi

if ! package_exists java-1.8.0-openjdk ; then
  yum -y install java-1.8.0-openjdk-devel.x86_64
fi

mkdir -p $NGINX_ADMIN_HOME
mkdir -p $NGINX_ADMIN_CONF
mkdir -p $NGINX_ADMIN_BIN
mkdir -p $NGINX_ADMIN_LOG

printf 'NGINX_ADMIN_HOME=%s\nNGINX_ADMIN_BIN=%s\nNGINX_ADMIN_LOG=%s\nNGINX_ADMIN_VERSION=%s\nNGINX_ADMIN_USER=nginx-admin\nNGINX_ADMIN_PORT=3000\n' $NGINX_ADMIN_HOME $NGINX_ADMIN_BIN $NGINX_ADMIN_LOG $NGINX_ADMIN_VERSION  > $NGINX_ADMIN_CONF/nginx-admin.conf

if ! file_exists "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then 
	wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar -O $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
fi

useradd -s /sbin/nologin nginx-admin
chown -R nginx-admin:nginx-admin $NGINX_ADMIN_HOME

if ! file_exists "/etc/init.d/nginx-admin" ; then 
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/install/red-hat/nginx-admin.sh -O /etc/init.d/nginx-admin
	chmod +x /etc/init.d/nginx-admin
	chown root:root /etc/init.d/nginx-admin
	chkconfig --level 345 nginx-admin on
fi

service nginx-admin start

echo "Nginx admin was successfully installed on $NGINX_ADMIN_HOME."