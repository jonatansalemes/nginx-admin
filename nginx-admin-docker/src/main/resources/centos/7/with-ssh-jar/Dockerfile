FROM centos:7
RUN yum -y update
RUN yum -y install openssh-server
RUN ssh-keygen -A
RUN echo root:123456 | chpasswd
COPY bin/nginx-admin-standalone-1.0.1-swarm.jar /nginx-admin-standalone-1.0.1-swarm.jar 
EXPOSE 22
