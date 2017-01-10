[![][travis img]][travis]
[![][license img]][license]

[travis]:https://travis-ci.org/jslsolucoes/nginx-admin
[travis img]:https://travis-ci.org/jslsolucoes/nginx-admin.svg?branch=master

[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg


# nginx-admin
Nginx admin is an open source multiplatform manager for nginx software to easy administration 


Screenshots : 

![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/master/nginx-admin-ui-screenshot/screenshot1.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/master/nginx-admin-ui-screenshot/screenshot2.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/master/nginx-admin-ui-screenshot/screenshot3.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/master/nginx-admin-ui-screenshot/screenshot4.png)
![alt tag](https://github.com/jslsolucoes/nginx-admin/blob/master/nginx-admin-ui-screenshot/screenshot5.png)

 
Supported operational system :
<ul>
	<li>Windows</li>
	<li>Linux</li>
	<li>Mac OSX</li>
</ul>

Some features :
<ul>
	<li>Test configuration,Stop,Start,Status,Reload and Restart</li>
	<li>Manager serves,ssl certificate,upstreams and virtual domains</li>
	<li>Multiplatform</li>
	<li>100% Java</li>
</ul>


<hr/>
Automatic installation :

Red-hat distribution you follow this steps as root user : 
<pre>
	<code>
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/scripts/red-hat/install.sh
chmod +x install.sh
./install.sh 
	</code>
</pre>


Debian distribution you follow this steps as root user : 
<pre>
	<code>
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/scripts/debian/install.sh
chmod +x install.sh
./install.sh 
	</code>
</pre>

<hr/>
Automatic update version :

Red-hat distribution you follow this steps as root user : 
<pre>
	<code>
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/scripts/red-hat/update.sh
chmod +x update.sh
./update.sh 
	</code>
</pre>

Debian distribution you follow this steps as root user : 
<pre>
	<code>
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/scripts/debian/update.sh
chmod +x update.sh
./update.sh 
	</code>
</pre>

<hr/>
Manual installation: 

* Download latest version of manager hosted on <a href='https://bintray.com/jslsolucoes/nginx-admin/com.jslsolucoes.nginx.admin/'>bintray</a> 
* Install nginx (required)
* Install jdk8 (required)
* Run manager with super user privileges : java -jar nginx-admin-standalone-{version}-swarm.jar
* h2 database is located under ~/database/nginx-admin (it`s important keep backup of this folder)
* Access localhost:3000 to access manager
* Enjoy

<h1>For premium support please contact : jonatan@jslsolucoes.com</h2>


