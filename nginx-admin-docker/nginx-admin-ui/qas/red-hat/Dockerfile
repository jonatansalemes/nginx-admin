FROM centos:7
RUN yum -y update
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 unzip python-setuptools
RUN easy_install supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-admin -r
RUN mkdir -p /opt/downloads
COPY build/nginx-admin-bin.zip /opt/downloads/nginx-admin.zip
RUN unzip /opt/downloads/nginx-admin.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin
RUN chown -R nginx-admin:nginx-admin /opt/nginx-admin
EXPOSE 4000
EXPOSE 4443
CMD ["/usr/bin/supervisord"]