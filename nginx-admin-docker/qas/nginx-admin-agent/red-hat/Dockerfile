FROM centos:7
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-admin-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-admin-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\nDefaults:nginx-admin-agent !requiretty\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN mkdir -p /opt/downloads
COPY build/nginx-admin-agent-2.0.3.zip /opt/downloads/nginx-admin-agent-2.0.3.zip
RUN unzip /opt/downloads/nginx-admin-agent-2.0.3.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-agent-2.0.3
RUN chown -R nginx-admin-agent:nginx-admin-agent /opt/nginx-admin-agent-2.0.3
ENV NGINX_ADMIN_AGENT_HOME /opt/nginx-admin-agent-2.0.3
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/bin/supervisord"]