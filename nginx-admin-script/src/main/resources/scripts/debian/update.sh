#!/bin/sh
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

function file_exists(){
    test -e $1
}

function download() {
    wget -S -q -O "$2" "$1" 2>&1 | grep "HTTP/" | awk '{print $2}' | grep 200
}


NGINX_ADMIN_HOME=/opt/nginx-admin
NGINX_ADMIN_BIN=$NGINX_ADMIN_HOME/bin
NGINX_ADMIN_VERSION=1.0.6

if ! file_exists "/etc/init.d/nginx-admin" ; then 
	echo "Nginx admin seems not be installed yet"
	exit
fi

if ! file_exists "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then
	if download "https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then 
		chown nginx-admin:nginx-admin $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
		sed -i "s/NGINX_ADMIN_VERSION=\(.*\)/NGINX_ADMIN_VERSION=$NGINX_ADMIN_VERSION/g" $NGINX_ADMIN_HOME/conf/nginx-admin.conf
		service nginx-admin restart
		echo "Nginx admin was successfully updated to latest version $NGINX_ADMIN_VERSION."
	else
	    rm -rf $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
    	echo "Error on download latest version $NGINX_ADMIN_VERSION. Please try again later on check your internet connection"
    	exit
	fi
else 
	echo "Your have already latest version $NGINX_ADMIN_VERSION. Nothing to do."
fi