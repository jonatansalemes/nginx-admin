[![][travis img]][travis]
[![][license img]][license]

[travis]:https://travis-ci.org/jslsolucoes/nginx-admin
[travis img]:https://travis-ci.org/jslsolucoes/nginx-admin.svg?branch=master

[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg


# nginx-admin
Nginx admin is an open source multiplatform manager for nginx software to easy administration 
 
Tested at moment in Operational system :
<ul>
	<li>Windows</li>
	<li>Linux CentOs</li>
	<li>Linux Ubuntu</li>
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

Red-hat distribution you follow this step as root user : 
<pre>
	<code>
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/install/red-hat/install.sh
chmod +x install.sh
./install.sh 
	</code>
</pre>

<hr/>
Manual installation: 

* Download latest version of manager hosted on <a href='https://bintray.com/jslsolucoes/nginx-admin/com.jslsolucoes.nginx.admin/'>bintray</a> 
* Install nginx (required)
* Install jdk8 (required)
* Run command passing as database user and password : java -jar nginx-admin-standalone-{version}-swarm.jar -p port
* h2 database is located under ../database/nginx of directory you are running (it`s important keep backup of this folder)
* Access localhost:port to access manager
* Enjoy

<h1>For premium support please contact : jonatan@jslsolucoes.com</h2>


