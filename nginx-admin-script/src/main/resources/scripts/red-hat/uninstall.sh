#!/bin/bash
service nginx-admin stop
rm -rf /opt/nginx-admin
rm -rf /etc/init.d/nginx-admin
userdel -f nginx-admin
sed -i 's/nginx-admin ALL = NOPASSWD: \/usr\/sbin\/nginx,\/usr\/bin\/pgrep//g' /etc/sudoers
sed -i 's/Defaults:nginx-admin !requiretty//g' /etc/sudoers