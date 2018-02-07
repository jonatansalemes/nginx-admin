FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk unzip python-setuptools
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
CMD ["/usr/local/bin/supervisord"]