#!/bin/sh
#
# Nginx Admin control script
#
# chkconfig: - 80 20
# description: Nginx Admin startup script
# processname: nginx-admin
# pidfile: /var/run/nginx-admin/nginx-admin.pid
# configfile: /opt/nginx-admin/conf/nginx-admin.conf 

. /etc/init.d/functions
. /opt/nginx-admin/conf/nginx-admin.conf

NGINX_ADMIN_NAME=nginx-admin
NGINX_ADMIN_PIDFILE=/var/run/${NGINX_ADMIN_NAME}/nginx-admin.pid
NGINX_ADMIN_CONSOLE_LOG=$NGINX_ADMIN_LOG/console.log
NGINX_ADMIN_LOCKFILE=/var/lock/subsys/${NGINX_ADMIN_NAME}

start() {
	echo -n "Starting $NGINX_ADMIN_NAME: "
	if [ -f $NGINX_ADMIN_PIDFILE ]; then
		read ppid < $NGINX_ADMIN_PIDFILE
		if [ `ps --pid $ppid 2> /dev/null | grep -c $ppid 2> /dev/null` -eq '1' ]; then
			echo -n "$NGINX_ADMIN_NAME is already running"
			failure
		echo
			return 1
		else
			rm -f $NGINX_ADMIN_CONSOLE_LOG
		fi
	fi
	mkdir -p $(dirname $NGINX_ADMIN_CONSOLE_LOG)
	cat /dev/null > $NGINX_ADMIN_CONSOLE_LOG

	mkdir -p $(dirname $NGINX_ADMIN_PIDFILE)
	chown root $(dirname $NGINX_ADMIN_PIDFILE) || true


	java -jar $NGINX_ADMIN_BIN/nginx-admin-standalone-1.0.0-swarm.jar -dbuser $NGINX_ADMIN_DB_USER -dbpassword $NGINX_ADMIN_DB_PASSWORD >> $NGINX_ADMIN_CONSOLE_LOG 2>&1 & echo $! > $NGINX_ADMIN_PIDFILE
	
	touch $NGINX_ADMIN_LOCKFILE
	success
	echo
	return 0
}

stop() {
	echo -n $"Stopping $NGINX_ADMIN_NAME: "
	count=0;

	if [ -f $NGINX_ADMIN_PIDFILE ]; then
		read kpid < $NGINX_ADMIN_PIDFILE
		let kwait=30

		# Try issuing SIGTERM
		kill -15 $kpid
		until [ `ps --pid $kpid 2> /dev/null | grep -c $kpid 2> /dev/null` -eq '0' ] || [ $count -gt $kwait ]
			do
			sleep 1
			let count=$count+1;
		done

		if [ $count -gt $kwait ]; then
			kill -9 $kpid
		fi
	fi
	rm -f $NGINX_ADMIN_PIDFILE
	rm -f $NGINX_ADMIN_LOCKFILE
	success
	echo
}

status() {
	if [ -f $NGINX_ADMIN_PIDFILE ]; then
		read ppid < $NGINX_ADMIN_PIDFILE
		if [ `ps --pid $ppid 2> /dev/null | grep -c $ppid 2> /dev/null` -eq '1' ]; then
			echo "$NGINX_ADMIN_NAME is running (pid $ppid)"
			return 0
		else
			echo "$NGINX_ADMIN_NAME dead but pid file exists"
			return 1
		fi
	fi
	echo "$NGINX_ADMIN_NAME is not running"
	return 3
}

case "$1" in
	start)
		start
		;;
	stop)
		stop
		;;
	restart)
		$0 stop
		$0 start
		;;
	status)
		status
		;;
	*)
		
		echo "Usage: $0 {start|stop|status|restart}"
		exit 1
		;;
esac