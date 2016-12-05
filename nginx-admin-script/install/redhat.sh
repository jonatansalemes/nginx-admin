#!/bin/bash
printf '[nginx]\nname=nginx repo\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/\ngpgcheck=0\nenabled=1' > /etc/yum.repos.d/nginx.repo
yum -y update
yum -y install nginx java-1.8.0-openjdk-devel.x86_64
java -jar nginx-admin-standalone-1.0.0-swarm.jar -bind 0.0.0.0 -port 3000 -dbuser sa -dbpassword sa
