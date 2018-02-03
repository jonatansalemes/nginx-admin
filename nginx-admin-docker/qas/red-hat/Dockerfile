FROM centos:7
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo python-setuptools
RUN easy_install supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN mkdir -p /usr/share/downloads
COPY build/nginx-agent-bin.zip /usr/share/downloads/nginx-agent.zip
RUN unzip /usr/share/downloads/nginx-agent.zip -d /opt
RUN chmod -R 755 /opt/nginx-agent
RUN chown -R nginx-agent:nginx-agent /opt/nginx-agent
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/bin/supervisord"]