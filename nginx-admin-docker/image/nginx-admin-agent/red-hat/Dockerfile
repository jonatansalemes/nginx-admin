FROM centos:7
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo python-setuptools wget
RUN easy_install supervisor
RUN printf '[supervisord]\nnodaemon=true\n\n[program:nginx-admin-agent]\ncommand = /opt/nginx-admin-agent-2.0.3/scripts/red-hat/nginx-admin-agent.sh start\n' >> /etc/supervisord.conf
RUN useradd nginx-admin-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-admin-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\nDefaults:nginx-admin-agent !requiretty\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN mkdir -p /opt/downloads
RUN wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-agent-2.0.3.zip -O /opt/downloads/nginx-admin-agent-2.0.3.zip
RUN unzip /opt/downloads/nginx-admin-agent-2.0.3.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-agent-2.0.3
RUN chown -R nginx-admin-agent:nginx-admin-agent /opt/nginx-admin-agent-2.0.3
ENV NGINX_ADMIN_AGENT_HOME /opt/nginx-admin-agent-2.0.3
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/bin/supervisord"]