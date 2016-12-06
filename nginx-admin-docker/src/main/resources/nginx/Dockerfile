FROM centos:6
RUN mkdir -p /usr/share/scripts
ADD scripts /usr/share/scripts
RUN chmod +x /usr/share/scripts/red-hat/install.sh
RUN /bin/bash /usr/share/scripts/red-hat/install.sh
EXPOSE 3000
EXPOSE 80
