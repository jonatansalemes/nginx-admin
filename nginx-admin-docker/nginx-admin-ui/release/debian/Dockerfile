FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openssh-server
RUN mkdir /var/run/sshd
RUN ssh-keygen -A 
RUN echo root:123456 | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
EXPOSE 22
EXPOSE 4000
EXPOSE 4443
CMD ["/usr/sbin/sshd", "-D"]