#!/bin/bash 
function package_exists(){
    if which $1 > /dev/null; then 
     	return $true
    else
        return $false
    fi
}

function file_exists(){
    if [ -e $1 ]; then
        return $true
    else
        return $false
    fi
}

function create_user() {
	MAXID=$(dscl . -read /Users/nobody UniqueID | awk '{print $2}' | sort -ug | tail -1)
	USERID=$((MAXID-1))
	
	sudo dscl . -create /Users/$1
	sudo dscl . -create /Users/$1 UserShell /usr/bin/false
	sudo dscl . -create /Users/$1 RealName "$1 user"
	sudo dscl . -create /Users/$1 UniqueID $USERID
	sudo dscl . -create /Users/$1 PrimaryGroupID $USERID
	sudo dscl . -create /Users/$1 NFSHomeDirectory /var/empty
}

NGINX_ADMIN_HOME=/opt/nginx-admin
NGINX_ADMIN_CONF=$NGINX_ADMIN_HOME/conf
NGINX_ADMIN_BIN=$NGINX_ADMIN_HOME/bin
NGINX_ADMIN_LOG=$NGINX_ADMIN_HOME/log
NGINX_ADMIN_VERSION=1.0.1

if ! package_exists brew ; then 
	echo "installing brew ..."
	/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
	brew update
fi

if ! package_exists wget ; then 
	echo "installing wget ..."
	brew install wget
fi 

if ! package_exists nginx ; then 
  echo "installing nginx ..."
  brew install nginx
fi

if ! package_exists java ; then
  brew cask install java
fi

sudo mkdir -p $NGINX_ADMIN_HOME
sudo mkdir -p $NGINX_ADMIN_CONF
sudo mkdir -p $NGINX_ADMIN_BIN
sudo mkdir -p $NGINX_ADMIN_LOG

if ! file_exists "$NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar" ; then 
	sudo wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar -O $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar
fi

create_user nginx
create_user nginx-admin

if ! file_exists "/Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist" ; then 
	wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/develop/nginx-admin-script/src/main/resources/install/mac/com.jslsolucoes.nginx.admin.plist -O /Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist
	sudo launchctl load -w /Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist
fi


