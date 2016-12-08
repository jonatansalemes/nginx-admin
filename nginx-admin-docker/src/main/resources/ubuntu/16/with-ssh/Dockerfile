FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openssh-server
RUN sed -i "s/PermitRootLogin prohibit-password/PermitRootLogin yes/g" /etc/ssh/sshd_config
RUN echo root:123456 | chpasswd
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]