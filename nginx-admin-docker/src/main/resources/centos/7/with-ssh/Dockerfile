FROM centos:7
RUN yum -y update
RUN yum -y install openssh-server
RUN ssh-keygen -A
RUN echo root:123456 | chpasswd
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
