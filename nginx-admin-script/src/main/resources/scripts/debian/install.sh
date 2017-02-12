#!/bin/bash 
#*******************************************************************************
# Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#*******************************************************************************

function package_exists(){
    dpkg -l $1 > /dev/null 2>&1
}

function file_exists(){
    test -e $1
}

function download() {
    wget -S -q -O "$2" "$1" 2>&1 | grep "HTTP/" | awk '{print $2}' | grep 200
}

NGINX_ADMIN_HOME=/opt/nginx-admin
NGINX_ADMIN_CONF=$NGINX_ADMIN_HOME/conf
NGINX_ADMIN_BIN=$NGINX_ADMIN_HOME/bin
NGINX_ADMIN_LOG=$NGINX_ADMIN_HOME/log
NGINX_ADMIN_VERSION=1.0.7


echo "Can i update your distribution first with apt-get -y update [y]:"
read update

if [ "$update" == "y" ] || [ "$update" == "" ]  ; then 
	apt-get -y update
fi

if ! package_exists sudo ; then 
	echo "installing sudo ..."
	apt-get -y install sudo
	chmod 640 /etc/sudoers
fi 

if ! package_exists wget ; then
	echo "installing wget ..."
	apt-get -y install wget
fi 

if ! package_exists nginx ; then 
  echo "installing nginx ..."
  printf 'deb http://nginx.org/packages/ubuntu/ xenial nginx\ndeb-src http://nginx.org/packages/ubuntu/ xenial nginx' >> /etc/apt/sources.list
  apt-get -y install nginx
fi

if ! package_exists openjdk-8-jdk ; then
  echo "installing java 8 ..."
  apt-get -y install openjdk-8-jdk
fi

mkdir -p $NGINX_ADMIN_HOME
mkdir -p $NGINX_ADMIN_CONF
mkdir -p $NGINX_ADMIN_BIN
mkdir -p $NGINX_ADMIN_LOG

printf 'NGINX_ADMIN_HOME=%s\nNGINX_ADMIN_BIN=%s\nNGINX_ADMIN_LOG=%s\nNGINX_ADMIN_VERSION=%s\nNGINX_ADMIN_USER=nginx-admin\nNGINX_ADMIN_PORT=3000\n' $NGINX_ADMIN_HOME $NGINX_ADMIN_BIN $NGINX_ADMIN_LOG $NGINX_ADMIN_VERSION  > $NGINX_ADMIN_CONF/nginx-admin.conf

if ! file_exists "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then 
	wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar -O $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
fi

useradd -s /sbin/nologin nginx
useradd -s /sbin/nologin nginx-admin
chown -R nginx-admin:nginx-admin $NGINX_ADMIN_HOME
printf 'nginx-admin ALL = NOPASSWD: /usr/sbin/nginx,/usr/bin/pgrep\n' >> /etc/sudoers
printf 'Defaults:nginx-admin !requiretty\n' >> /etc/sudoers

if ! file_exists "/etc/init.d/nginx-admin" ; then 
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/scripts/debian/nginx-admin.sh -O /etc/init.d/nginx-admin
	chmod +x /etc/init.d/nginx-admin
	chown root:root /etc/init.d/nginx-admin
	update-rc.d nginx-admin defaults
	update-rc.d nginx-admin enable
fi

service nginx-admin start

echo "Nginx admin was successfully started and installed on $NGINX_ADMIN_HOME. If you can check file $NGINX_ADMIN_CONF/nginx-admin.conf for configurations of service"