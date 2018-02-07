FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openssh-server
RUN mkdir /var/run/sshd
RUN ssh-keygen -A 
RUN echo root:123456 | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
EXPOSE 22
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/sbin/sshd", "-D"]