FROM centos:7
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install psmisc initscripts java-1.8.0-openjdk-devel.x86_64 nginx unzip sudo wget
RUN mkdir -p /usr/share/downloads
RUN wget https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-agent-bin.zip -O /usr/share/downloads/nginx-agent-bin.zip
RUN useradd nginx-agent -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN unzip /usr/share/downloads/nginx-agent-bin.zip -d /opt
RUN chmod -R 755 /opt/nginx-agent
RUN chown -R nginx-agent:nginx-agent /opt/nginx-agent
EXPOSE 80
EXPOSE 3000
EXPOSE 3443