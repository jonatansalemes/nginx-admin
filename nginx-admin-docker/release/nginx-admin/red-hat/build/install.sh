#!/bin/sh

#check for root
if [ `id -u` -ne 0 ]; then
	echo "You need root privileges to run this script"
	exit 1
fi

#install pre-dependencies if has no one
yum -y update
yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 unzip wget firewalld

#create user for running manager
useradd nginx-admin -r

#download and extract latest version of nginx manager package
mkdir -p /opt/downloads
wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-2.0.2.zip -O /opt/downloads/nginx-admin-2.0.2.zip
unzip /opt/downloads/nginx-admin-2.0.2.zip -d /opt
chmod -R 755 /opt/nginx-admin-2.0.2
chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.2

#set environment variable
printf 'NGINX_ADMIN_HOME=/opt/nginx-admin-2.0.2\n' >> /etc/environment

#add init scripts to os
cp /opt/nginx-admin-2.0.2/scripts/red-hat/nginx-admin.sh /etc/init.d/nginx-admin
chmod +x /etc/init.d/nginx-admin
chown root:root /etc/init.d/nginx-admin
chkconfig --level 345 nginx-admin on

#release ports on firewalld to nginx-agent and to nginx	
printf '&lt;?xml version="1.0" encoding="utf-8"?&gt;\n&lt;service&gt;\n&lt;short&gt;Nginx admin firewall service&lt;/short&gt;\n&lt;description&gt;Nginx admin firewall service&lt;/description&gt;\n&lt;port protocol="tcp" port="4000"/&gt;\n&lt;port protocol="tcp" port="4443"/&gt;\n&lt;/service&gt;\n' &gt;&gt; /etc/firewalld/services/nginx-admin.xml
firewall-cmd --zone=public --add-service=nginx-admin --permanent
firewall-cmd --reload

#start service
service nginx-admin start

#logs from server
tail -f /opt/nginx-admin-2.0.2/log/console.log

#You can check for manager ui in browser accessing http://localhost:4000
#Please access /opt/nginx-admin-2.0.2/conf/nginx-admin.conf and you can see or change others configurations 
#like smtp settings or change database driver connection (NGINX_ADMIN_DB_DRIVER=(h2 or mysql)) if you like in this file and restart service to apply new settings