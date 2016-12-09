#!/bin/bash 
apt-get -y update

function package_exists(){
    dpkg -l $1 > /dev/null 2>&1
    INSTALLED=$?
    if [ $INSTALLED == '0' ]; then
       return $true
    else
       return $false
    fi
}

if ! package_exists wget ; then
	echo "installing wget ..."
	apt-get -y install wget
fi 

if ! package_exists nginx ; then 
  echo "installing nginx ..."
  printf 'deb http://nginx.org/packages/ubuntu/ xenial nginx\ndeb-src http://nginx.org/packages/ubuntu/ xenial nginx' >> /etc/apt/sources.list
  apt-get -y install nginx
fi

if ! package_exists openjdk-8-jdk ; then
  echo "installing java 8 ..."
  apt-get -y install openjdk-8-jdk
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
	
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/install/debian/nginx-admin-init-debian.sh -O /etc/init.d/nginx-admin
	sed -i "s/dbuser \(\w*\)/dbuser $dbuser/g" /etc/init.d/nginx-admin
	sed -i "s/dbpassword \(\w*\)/dbpassword $dbpassword/g" /etc/init.d/nginx-admin
	chmod +x /etc/init.d/nginx-admin
	chown root:root /etc/init.d/nginx-admin
	update-rc.d nginx-admin defaults
	update-rc.d nginx-admin enable
fi