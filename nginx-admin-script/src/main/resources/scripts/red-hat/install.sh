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
#!/bin/bash 
function package_exists(){
    rpm -q --quiet $1
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
NGINX_ADMIN_VERSION=1.0.5


echo "Can i update your distribution first with yum -y update [y]:"
read update

if [ "$update" == "y" ] || [ "$update" == "" ]  ; then 
	yum -y update
fi

if ! package_exists psmisc ; then 
	echo "installing psmisc ..."
	yum -y install psmisc
fi


if ! package_exists initscripts ; then 
	echo "installing initscripts ..."
	yum -y install initscripts
fi

if ! package_exists sudo ; then 
	echo "installing sudo ..."
	yum -y install sudo
	chmod 640 /etc/sudoers
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
printf 'nginx-admin ALL = NOPASSWD: /usr/sbin/nginx,/usr/bin/pgrep\n' >> /etc/sudoers
printf 'Defaults:nginx-admin !requiretty\n' >> /etc/sudoers

if ! file_exists "/etc/init.d/nginx-admin" ; then 
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/install/red-hat/nginx-admin.sh -O /etc/init.d/nginx-admin
	chmod +x /etc/init.d/nginx-admin
	chown root:root /etc/init.d/nginx-admin
	chkconfig --level 345 nginx-admin on
fi

service nginx-admin start

echo "Nginx admin was successfully started and installed on $NGINX_ADMIN_HOME. If you can check file $NGINX_ADMIN_CONF/nginx-admin.conf for configurations of service"