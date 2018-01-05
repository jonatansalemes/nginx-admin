FROM centos:7
RUN mkdir -p /usr/share/downloads
COPY build/nginx.repo /etc/yum.repos.d/nginx.repo
RUN yum -y update
RUN yum -y install openssh-server psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx python-setuptools unzip
RUN easy_install supervisor
RUN ssh-keygen -A
RUN echo root:123456 | chpasswd
COPY build/supervisord.conf /etc/supervisord.conf
COPY build/nginx-agent-bin.zip /usr/share/downloads/nginx-agent.zip
RUN unzip /usr/share/downloads/nginx-agent.zip -d /opt
CMD ["/usr/bin/supervisord"]
EXPOSE 22
EXPOSE 80
EXPOSE 3000