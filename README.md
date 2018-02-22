[![][travis img]][travis]
[![][license img]][license]
[![][sonar img]][sonar]
[![][bugs img]][bugs]
[![][coverage img]][coverage]
[![][lines img]][lines]
[![][vulnerabilities img]][vulnerabilities]
[![][code_smells img]][code_smells]
[![][paypal img]][paypal]

[travis]:https://travis-ci.org/jslsolucoes/nginx-admin
[travis img]:https://travis-ci.org/jslsolucoes/nginx-admin.svg?branch=develop

[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg

[sonar]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[sonar img]:https://sonarcloud.io/api/badges/gate?key=com.jslsolucoes:nginx-admin:develop

[bugs]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[bugs img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=bugs

[coverage]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[coverage img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=coverage

[bugs]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[bugs img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=bugs

[lines]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[lines img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=lines

[vulnerabilities]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[vulnerabilities img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=vulnerabilities

[code_smells]:https://sonarcloud.io/dashboard/index/com.jslsolucoes:nginx-admin:develop
[code_smells img]:https://sonarcloud.io/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=code_smells

[paypal]:https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=PE25DPU3CNFH4
[paypal img]:https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif

# nginx-admin
Nginx admin is an open source multiplatform manager for nginx software to easy administration 

Welcome to the new brand Nginx Admin 2.x-RC1 with a lot of improvments and bug fixes. 
This milestone develop was a great breakthrough in system arch and unfortunately break compatibility with old 1.x version. Sorry for that.
You can check new way to install components arch and new features like manager multiple nginx in a single manager ui.

Screenshots: 

![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/develop/nginx-admin-ui-screenshot/screenshot1.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/develop/nginx-admin-ui-screenshot/screenshot2.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/develop/nginx-admin-ui-screenshot/screenshot3.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/develop/nginx-admin-ui-screenshot/screenshot4.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/develop/nginx-admin-ui-screenshot/screenshot5.png)

 
Features:

<ul>
	<li>Test configuration,Kill,Stop,Start,Status,Reload and Restart</li>
	<li>Support for manager multiple nginx agent (nodes) in a single manager ui interface</li>
	<li>Manager servers endpoints,ssl certificate,upstreams and virtual hosts</li>
	<li>Multiplatform support for manager</li>
	<li>100% Java</li>
	<li>Rotate and collect errors and access logs</li>
	<li>Log reports and stats</li>
</ul>

Supported operational system for agent:
<ul>
	<li>Linux</li>
</ul>

Supported operational system for manager:
<ul>
	<li>Linux</li>
</ul>

Supported database for manager: (Another databases like postgresql,oracle and sqlserver will be released soon as possible)
<ul>
	<li>H2</li>
	<li>MySQL</li>
</ul>


<hr/>
Installation: (Always as root user)

Nginx agent (node):

Red-hat:
<pre>
	<code>
		#install pre-dependencies if has no one
		yum -y update
		yum -y install epel-release
		yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo wget visudo vim firewalld
	
		#create user and add permission for running agent. you can also use visudo to add permissions below
		useradd nginx-agent -r
		chmod 640 /etc/sudoers
		printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
		chmod 440 /etc/sudoers
		
		#download and extract latest version of nginx agent package
		mkdir -p /opt/downloads
		wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-agent-2.0.1-bin.zip -O /opt/downloads/nginx-agent-bin.zip
		unzip /opt/downloads/nginx-agent-bin.zip -d /opt
		chmod -R 755 /opt/nginx-agent
		chown -R nginx-agent:nginx-agent /opt/nginx-agent
		
		#add init scripts to os
		cp /opt/nginx-agent/scripts/red-hat/nginx-agent.sh /etc/init.d/nginx-agent
		chmod +x /etc/init.d/nginx-agent
		chown root:root /etc/init.d/nginx-agent
		chkconfig --level 345 nginx-agent on
		
		#start service
		service nginx-agent start
		
		#release ports on firewalld to nginx-agent and to nginx	
		printf '<?xml version="1.0" encoding="utf-8"?>\n<service>\n<short>Nginx agent firewall service</short>\n<description>Nginx agent firewall service</description>\n<port protocol="tcp" port="3000"/>\n<port protocol="tcp" port="3443"/>\n</service>\n' >> /etc/firewalld/services/nginx-agent.xml
		firewall-cmd --zone=public --add-service=nginx-agent --permanent
		firewall-cmd --zone=public --add-service=http --permanent
		firewall-cmd --zone=public --add-service=https --permanent
		firewall-cmd --reload
		
		#you can check for connectivity after a few seconds that must return http status 200 OK for the request
		wget --header "Authorization: changeit" http://localhost:3000
		
		#By default your authorization key is "changeit" you also can check for connectivity in your browser accessing http://ip:3000 or https://ip:3443 that will return http status 403 forbidden with message "Resource forbidden" because requires authorization header to work
		#Please access /opt/nginx-agent/conf/nginx-agent.conf and change variable NGINX_AGENT_AUTHORIZATION_KEY=changeit value for one that only you knows and unique for every node that you have installed. You can also change others configurations if you like in this file and restart service to apply new settings
		#Nginx manager ui will ask for this authorization key value to connect with this agent.
		
	</code>
</pre>

Debian:
<pre>
	<code>
		#install pre-dependencies if has no one
		apt-get -y update
		apt-get -y install openjdk-8-jdk nginx unzip sudo wget vim
		
		#stop default nginx
		service nginx stop
		
		#create user and add permission for running agent. you can also use visudo to add permissions below
		useradd nginx-agent -r
		useradd nginx -r
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
		cp /opt/nginx-agent-2.0.1/scripts/debian/nginx-agent.sh /etc/init.d/nginx-agent
		chmod +x /etc/init.d/nginx-agent
		chown root:root /etc/init.d/nginx-agent
		update-rc.d nginx-agent defaults
		update-rc.d nginx-agent enable
		
		#start service
		service nginx-agent start
		
		#you can check for connectivity after a few second that must return http status 200 OK for the request
		wget --header "Authorization: changeit" http://localhost:3000
		
		#By default your authorization key is "changeit" you also can check for connectivity in your browser accessing http://ip:3000 or https://ip:3443 that will return http status 403 forbidden with message "Resource forbidden" because requires authorization header to work
		#Please access /opt/nginx-agent/conf/nginx-agent.conf and change variable NGINX_AGENT_AUTHORIZATION_KEY=changeit value for one that only you knows and unique for every node that you have installed. You can also change others configurations if you like in this file and restart service to apply new settings
		#Nginx manager ui will ask for this authorization key value to connect with this agent.
		
	</code>
</pre>


Nginx admin ui (manager): (you can install manager in the same machine or diferent machine then agent above).
The default user for manager is: 

Login :    admin
Password : admin


Red-hat:
<pre>
	<code>
		#install pre-dependencies if has no one
		yum -y update
		yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 unzip wget firewalld
	
		#create user for running manager
		useradd nginx-admin -r
		
		#download and extract latest version of nginx manager package
		mkdir -p /opt/downloads
		wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-2.0.1-bin.zip -O /opt/downloads/nginx-admin-bin.zip
		unzip /opt/downloads/nginx-admin-bin.zip -d /opt
		chmod -R 755 /opt/nginx-admin
		chown -R nginx-admin:nginx-admin /opt/nginx-admin
		
		#add init scripts to os
		cp /opt/nginx-admin/scripts/red-hat/nginx-admin.sh /etc/init.d/nginx-admin
		chmod +x /etc/init.d/nginx-admin
		chown root:root /etc/init.d/nginx-admin
		chkconfig --level 345 nginx-admin on
		
		#start service
		service nginx-admin start
		
		#release ports on firewalld to nginx-agent and to nginx	
		printf '<?xml version="1.0" encoding="utf-8"?>\n<service>\n<short>Nginx admin firewall service</short>\n<description>Nginx admin firewall service</description>\n<port protocol="tcp" port="4000"/>\n<port protocol="tcp" port="4443"/>\n</service>\n' >> /etc/firewalld/services/nginx-admin.xml
		firewall-cmd --zone=public --add-service=nginx-admin --permanent
		firewall-cmd --reload
		
		#You can check for manager ui in browser accessing http://localhost:4000
		#Please access /opt/nginx-admin/conf/nginx-admin.conf and you can see or change others configurations 
		#like smtp settings or change database driver connection (NGINX_ADMIN_DB_DRIVER=(h2 or mysql)) if you like in this file and restart service to apply new settings
		
	</code>
</pre>

Debian:
<pre>
	<code>
		#install pre-dependencies if has no one
		apt-get -y update
		apt-get -y install openjdk-8-jdk unzip wget
	
		#create user for running manager
		useradd nginx-admin -r
		
		#download and extract latest version of nginx manager package
		mkdir -p /opt/downloads
		wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-2.0.1.zip -O /opt/downloads/nginx-admin-2.0.1.zip
		unzip /opt/downloads/nginx-admin-2.0.1.zip -d /opt
		chmod -R 755 /opt/nginx-admin-2.0.1
		chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.1
		
		#set environment variable
		printf 'NGINX_ADMIN_HOME=/opt/nginx-admin-2.0.1\n' >> /etc/environment
		
		#add init scripts to os
		cp /opt/nginx-admin-2.0.1/scripts/debian/nginx-admin.sh /etc/init.d/nginx-admin
		chmod +x /etc/init.d/nginx-admin
		chown root:root /etc/init.d/nginx-admin
		update-rc.d nginx-admin defaults
		update-rc.d nginx-admin enable
		
		#start service
		service nginx-admin start
		
		#You can check for manager ui in browser accessing http://localhost:4000
		#Please access /opt/nginx-admin/conf/nginx-admin.conf and you can see or change others configurations 
		#like smtp settings or change database driver connection (NGINX_ADMIN_DB_DRIVER=(h2 or mysql)) if you like in this file and restart service to apply new settings
		
	</code>
</pre>

Thanks for using nginx-admin.

Issues or questions can be done in https://github.com/jslsolucoes/nginx-admin/issues. 

<h1>For premium support please contact : jonatan@jslsolucoes.com</h2>


