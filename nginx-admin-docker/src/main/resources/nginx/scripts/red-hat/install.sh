#!/bin/bash
yum -y update
if ! rpm -q --quiet dos2unix ; then 
	echo "installing dos2unix ..."
	yum -y install dos2unix
fi
if ! rpm -q --quiet wget ; then 
	echo "installing wget ..."
	yum -y install wget
fi
if ! rpm -q --quiet nginx ; then 
  echo "installing nginx ..."
  printf '[nginx]\nname=nginx repo\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/\ngpgcheck=0\nenabled=1' > /etc/yum.repos.d/nginx.repo
  yum -y install nginx
fi
if ! rpm -q --quiet java ; then
  echo "installing java 8 ..."
  yum -y install java-1.8.0-openjdk-devel.x86_64
fi
mkdir -p /usr/share/softwares
wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-1.0.0-swarm.jar -O /usr/share/softwares/nginx-admin-standalone-1.0.0-swarm.jar
wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/install/red-hat/nginx-admin-init-redhat.sh -O /etc/init.d/nginx-admin
dos2unix /etc/init.d/nginx-admin
chmod +x /etc/init.d/nginx-admin
chown root:root /etc/init.d/nginx-admin
