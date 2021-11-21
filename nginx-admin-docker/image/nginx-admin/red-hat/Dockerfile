FROM centos:7
RUN yum -y update
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 unzip supervisor wget
RUN printf '[supervisord]\nnodaemon=true\n\n[program:nginx-admin]\ncommand = /opt/nginx-admin-2.0.3/scripts/red-hat/nginx-admin.sh start\n' >> /etc/supervisord.conf
RUN useradd nginx-admin -r
RUN mkdir -p /opt/downloads
RUN wget https://github.com/jslsolucoes/nginx-admin/raw/master/nginx-admin-bin/nginx-admin-2.0.3.zip -O /opt/downloads/nginx-admin-2.0.3.zip
RUN unzip /opt/downloads/nginx-admin-2.0.3.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-2.0.3
RUN chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.3
ENV NGINX_ADMIN_HOME /opt/nginx-admin-2.0.3
EXPOSE 4000
EXPOSE 4443
CMD ["/usr/bin/supervisord"]