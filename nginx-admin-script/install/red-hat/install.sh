#!/bin/bash 
yum -y update

if ! rpm -q --quiet wget ; then 
	echo "installing wget ..."
	yum -y install wget
fi 

if ! rpm -q --quiet nginx ; then 
  echo "installing nginx ..."
  printf '[nginx]\nname=nginx repo\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/\ngpgcheck=0\nenabled=1' > /etc/yum.repos.d/nginx.repo
  yum -y install nginx
fi

if ! rpm -q --quiet java-1.8.0-openjdk ; then
  echo "installing java 8 ..."
  yum -y install java-1.8.0-openjdk-devel.x86_64
fi

mkdir -p /usr/share/softwares
if [ ! -e "/usr/share/softwares/nginx-admin-standalone-1.0.0-swarm.jar" ] ; then 
	wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-1.0.0-swarm.jar -O /usr/share/softwares/nginx-admin-standalone-1.0.0-swarm.jar
fi

if [ ! -e "/etc/init.d/nginx-admin" ] ; then 
	echo -n "Database user : "
	read dbuser
 
	echo -n "Database password : "
	read -s dbpassword
	
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/install/red-hat/nginx-admin-init-redhat.sh -O /etc/init.d/nginx-admin
	sed -i "s/dbuser \(\w*\)/dbuser $dbuser/g" /etc/init.d/nginx-admin
	sed -i "s/dbpassword \(\w*\)/dbpassword $dbpassword/g" /etc/init.d/nginx-admin
	chmod +x /etc/init.d/nginx-admin
	chown root:root /etc/init.d/nginx-admin
	chkconfig --level 345 nginx-admin on
fi