#!/bin/sh

#check for root
if [ `id -u` -ne 0 ]; then
	echo "You need root privileges to run this script"
	exit 1
fi

#install pre-dependencies if has no one
yum -y update
yum -y install epel-release
yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo wget visudo vim firewalld

#stop default nginx
service nginx stop

#create user and add permission for running agent. you can also use visudo to add permissions below
useradd nginx-agent -r
chmod 640 /etc/sudoers
printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
chmod 440 /etc/sudoers

#download and extract latest version of nginx agent package
mkdir -p /opt/downloads
wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-agent-2.0.1.zip -O /opt/downloads/nginx-agent-2.0.1.zip
unzip /opt/downloads/nginx-agent-2.0.1.zip -d /opt
chmod -R 755 /opt/nginx-agent-2.0.1
chown -R nginx-agent:nginx-agent /opt/nginx-agent-2.0.1

#set environment variable
printf 'NGINX_AGENT_HOME=/opt/nginx-agent-2.0.1\n' >> /etc/environment

#add init scripts to os
cp /opt/nginx-agent-2.0.1/scripts/red-hat/nginx-agent.sh /etc/init.d/nginx-agent
chmod +x /etc/init.d/nginx-agent
chown root:root /etc/init.d/nginx-agent
chkconfig --level 345 nginx-agent on

#release ports on firewalld to nginx-agent and to nginx	
printf '&lt;?xml version="1.0" encoding="utf-8"?&gt;\n&lt;service&gt;\n&lt;short&gt;Nginx agent firewall service&lt;/short&gt;\n&lt;description&gt;Nginx agent firewall service&lt;/description&gt;\n&lt;port protocol="tcp" port="3000"/&gt;\n&lt;port protocol="tcp" port="3443"/&gt;\n&lt;/service&gt;\n' &gt;&gt; /etc/firewalld/services/nginx-agent.xml
firewall-cmd --zone=public --add-service=nginx-agent --permanent
firewall-cmd --zone=public --add-service=http --permanent
firewall-cmd --zone=public --add-service=https --permanent
firewall-cmd --reload

#start service
service nginx-agent start

#logs from server
tail -f /opt/nginx-agent-2.0.1/log/console.log

#you can check for connectivity after a few seconds that must return http status 200 OK for the request
wget --header "Authorization: changeit" http://localhost:3000

#By default your authorization key is "changeit" you also can check for connectivity in your browser accessing http://ip:3000 or https://ip:3443 that will return http status 403 forbidden with message "Resource forbidden" because requires authorization header to work
#Please access /opt/nginx-agent-2.0.1/conf/nginx-agent.conf and change variable NGINX_AGENT_AUTHORIZATION_KEY=changeit value for one that only you knows and unique for every node that you have installed. You can also change others configurations if you like in this file and restart service to apply new settings
#Nginx manager ui will ask for this authorization key value to connect with this agent.