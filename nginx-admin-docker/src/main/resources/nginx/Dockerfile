FROM centos:6
RUN printf '[nginx]\nname=nginx repo\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/\ngpgcheck=0\nenabled=1' > /etc/yum.repos.d/nginx.repo
RUN yum -y update
RUN yum -y install nginx unzip wget tar
RUN mkdir -p /usr/share/downloads
RUN mkdir -p /usr/share/softwares

RUN wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-linux-x64.tar.gz -O /usr/share/downloads/jdk-8u111-linux-x64.tar.gz
RUN tar -xzvf /usr/share/downloads/jdk-8u111-linux-x64.tar.gz --directory /usr/share/softwares
RUN ln -s /usr/share/softwares/jdk1.8.0_111 /usr/share/java
ENV JAVA_HOME /usr/share/java
ENV PATH $PATH:$JAVA_HOME/bin

COPY bin/nginx-admin-standalone-swarm.jar /usr/share/softwares

EXPOSE 3000
EXPOSE 80
