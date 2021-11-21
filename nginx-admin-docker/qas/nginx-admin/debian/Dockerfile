FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk unzip supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-admin -r
RUN mkdir -p /opt/downloads
COPY build/nginx-admin-2.0.3.zip /opt/downloads/nginx-admin-2.0.3.zip
RUN unzip /opt/downloads/nginx-admin-2.0.3.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-2.0.3
RUN chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.3
ENV NGINX_ADMIN_HOME /opt/nginx-admin-2.0.3
EXPOSE 4000
EXPOSE 4443
CMD ["/usr/bin/supervisord"]