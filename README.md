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

[sonar]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[sonar img]:https://sonarqube.com/api/badges/gate?key=com.jslsolucoes:nginx-admin:develop

[bugs]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[bugs img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=bugs

[coverage]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[coverage img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=coverage

[bugs]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[bugs img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=bugs

[lines]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[lines img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=lines

[vulnerabilities]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[vulnerabilities img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=vulnerabilities

[code_smells]:https://sonarqube.com/dashboard/index/com.jslsolucoes:nginx-admin:develop
[code_smells img]:https://sonarqube.com/api/badges/measure?key=com.jslsolucoes:nginx-admin:develop&metric=code_smells

[paypal]:https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=PE25DPU3CNFH4
[paypal img]:https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif

# nginx-admin
Nginx admin is an open source multiplatform manager for nginx software to easy administration 

Welcome to the new brand Nginx Admin 2.x with a lot of improvments and bug fixes. 
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
	<li>Windows</li>
	<li>Mac</li>
</ul>

Supported database for manager: (Another databases like postgresql,oracle and sqlserver will be released soon as possible)
<ul>
	<li>H2</li>
	<li>MySQL</li>
</ul>


<hr/>
Installation: (Always as root user)

Nginx agent:

Red-hat:
<pre>
	<code>
		#install pre-dependencies if has no one
		yum -y update
		yum -y install epel-release
		yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo wget visudo
	
		#create user and add permission for running agent. you can also use visudo to add permissions below
		useradd nginx-agent -r
		chmod 640 /etc/sudoers
		printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
		chmod 440 /etc/sudoers
		
		#download and extract latest version of nginx agent package
		mkdir -p /opt/downloads
		wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-agent-bin.zip -O /opt/downloads/nginx-agent-bin.zip
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
		
		#you can check for connectivity that must return http status 200 OK for the request
		wget --header "Authorization: changeit" http://localhost:3000
		
		#By default your authorization key is "changeit" you also can check for connectivity in your browser accessing http://ip:3000 or https://ip:3443 that will return http status 403 forbidden with message "Resource forbidden" because requires authorization header to work
		#Please access /opt/nginx-agent/conf/nginx-agent.conf and change variable NGINX_AGENT_AUTHORIZATION_KEY=changeit value for one that only you knows and unique for every node that you have installed. You can also change others configurations if you like in this file
		#Nginx manager ui will ask for this authorization key value to connect with this agent.
		
	</code>
</pre>

Debian:
<pre>
	<code>
		#install pre-dependencies if has no one
		apt-get -y update
		apt-get -y install openjdk-8-jdk unzip nginx unzip sudo wget vim
		
		#create user and add permission for running agent. you can also use visudo to add permissions below
		useradd nginx-agent -r
		chmod 640 /etc/sudoers
		printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
		chmod 440 /etc/sudoers
		
		#download and extract latest version of nginx agent package
		mkdir -p /opt/downloads
		wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-agent-bin.zip -O /opt/downloads/nginx-agent-bin.zip
		unzip /opt/downloads/nginx-agent-bin.zip -d /opt
		chmod -R 755 /opt/nginx-agent
		chown -R nginx-agent:nginx-agent /opt/nginx-agent
		
		#add init scripts to os
		cp /opt/nginx-agent/scripts/debian/nginx-agent.sh /etc/init.d/nginx-agent
		chmod +x /etc/init.d/nginx-agent
		chown root:root /etc/init.d/nginx-agent
		update-rc.d nginx-agent defaults
	    update-rc.d nginx-agent enable
		
		#start service
		service nginx-agent start
		
		#you can check for connectivity that must return http status 200 OK for the request
		wget --header "Authorization: changeit" http://localhost:3000
		
		#By default your authorization key is "changeit" you also can check for connectivity in your browser accessing http://ip:3000 or https://ip:3443 that will return http status 403 forbidden with message "Resource forbidden" because requires authorization header to work
		#Please access /opt/nginx-agent/conf/nginx-agent.conf and change variable NGINX_AGENT_AUTHORIZATION_KEY=changeit value for one that only you knows and unique for every node that you have installed. You can also change others configurations if you like in this file
		#Nginx manager ui will ask for this authorization key value to connect with this agent.
		
	</code>
</pre>


<h1>For premium support please contact : jonatan@jslsolucoes.com</h2>


