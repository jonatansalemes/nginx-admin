FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk unzip python-setuptools wget
RUN easy_install supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-admin -r
RUN mkdir -p /opt/downloads
RUN wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-2.0.1.zip -O /opt/downloads/nginx-admin-2.0.1.zip
RUN unzip /opt/downloads/nginx-admin-2.0.1.zip -d /opt
RUN chmod -R 755 /opt/nginx-admin-2.0.1
RUN chown -R nginx-admin:nginx-admin /opt/nginx-admin-2.0.1
ENV NGINX_ADMIN_HOME /opt/nginx-admin-2.0.1
EXPOSE 4000
EXPOSE 4443
CMD ["/usr/local/bin/supervisord"]