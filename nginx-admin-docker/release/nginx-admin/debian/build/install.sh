#!/bin/sh

#check for root
if [ `id -u` -ne 0 ]; then
	echo "You need root privileges to run this script"
	exit 1
fi

#install pre-dependencies if has no one
apt-get -y update
apt-get -y install openjdk-8-jdk unzip wget

#create user for running manager
useradd nginx-admin -r

#download and extract latest version of nginx manager package
mkdir -p /opt/downloads
wget https://jslsolucoes.jfrog.io/artifactory/ngxin-admin-generic-local/nginx-admin-2.0.3.zip -O /opt/downloads/nginx-admin-2.0.3.zip
unzip /opt/downloads/nginx-admin-2.0.3.zip -d /opt
chmod -R 755 /opt/nginx-admin-2.0.3
chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.3

#set environment variable
printf 'NGINX_ADMIN_HOME=/opt/nginx-admin-2.0.3\n' >> /etc/environment

#add init scripts to os
cp /opt/nginx-admin-2.0.3/scripts/debian/nginx-admin.sh /etc/init.d/nginx-admin
chmod +x /etc/init.d/nginx-admin
chown root:root /etc/init.d/nginx-admin
update-rc.d nginx-admin defaults
update-rc.d nginx-admin enable

#start service
service nginx-admin start

#logs from server
tail -f /opt/nginx-admin-2.0.3/log/console.log

#You can check for manager ui in browser accessing http://localhost:4000
#Please access /opt/nginx-admin-2.0.3/conf/nginx-admin.conf and you can see or change others configurations 
#like smtp settings or change database driver connection (NGINX_ADMIN_DB_DRIVER=(h2 or mysql)) if you like in this file and restart service to apply new settings
