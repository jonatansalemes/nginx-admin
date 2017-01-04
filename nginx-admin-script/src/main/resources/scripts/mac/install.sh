#*******************************************************************************
# Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#*******************************************************************************
#!/bin/bash 
function package_exists(){
    if sudo which $1 > /dev/null; then 
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

function user_exists() {
	if sudo dscl . -read /Users/$1 > /dev/null; then 
     	return $true
    else
        return $false
    fi
}

function create_user() {
	MAXID=$(sudo dscl . -read /Users/$2 UniqueID | awk '{print $2}' | sort -ug | tail -1)
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

if ! user_exists nginx ; then 
	create_user nginx nobody
fi

if ! user_exists nginx-admin ; then 
	create_user nginx-admin nginx
fi

if ! file_exists "/Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist" ; then 
	sudo wget https://raw.githubusercontent.com/jslsolucoes/nginx-admin/master/nginx-admin-script/src/main/resources/install/mac/com.jslsolucoes.nginx.admin.plist -O /Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist
	sudo launchctl load -w /Library/LaunchDaemons/com.jslsolucoes.nginx.admin.plist
fi


