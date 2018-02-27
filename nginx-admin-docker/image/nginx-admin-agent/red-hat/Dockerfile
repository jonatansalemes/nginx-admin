FROM centos:7
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo python-setuptools wget
RUN easy_install supervisor
RUN printf '[supervisord]\nnodaemon=true\n\n[program:nginx-admin-agent]\ncommand = /opt/nginx-admin-agent-2.0.2/scripts/red-hat/nginx-admin-agent.sh start\n' >> /etc/supervisord.conf
RUN useradd nginx-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\nDefaults:nginx-agent !requiretty\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN mkdir -p /opt/downloads
RUN wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-agent-2.0.2.zip -O /opt/downloads/nginx-admin-agent-2.0.2.zip
RUN unzip /opt/downloads/nginx-admin-agent-2.0.2.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-agent-2.0.2
RUN chown -R nginx-agent:nginx-agent /opt/nginx-admin-agent-2.0.2
ENV NGINX_AGENT_HOME /opt/nginx-admin-agent-2.0.2
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/bin/supervisord"]