FROM centos:7
RUN mkdir -p /usr/share/downloads
COPY build/nginx.repo /etc/yum.repos.d/nginx.repo
RUN yum -y update
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx python-setuptools unzip sudo
RUN easy_install supervisor
RUN echo root:123456 | chpasswd
RUN useradd nginx-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-agent ALL = NOPASSWD: /usr/sbin/nginx,/usr/bin/pgrep,/usr/bin/killall\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
COPY build/supervisord.conf /etc/supervisord.conf
COPY build/nginx-agent-bin.zip /usr/share/downloads/nginx-agent.zip
RUN unzip /usr/share/downloads/nginx-agent.zip -d /opt
RUN chmod -R 755 /opt/nginx-agent
RUN chown -R nginx-agent:nginx-agent /opt/nginx-agent
CMD ["/usr/bin/supervisord"]
EXPOSE 80
EXPOSE 3000