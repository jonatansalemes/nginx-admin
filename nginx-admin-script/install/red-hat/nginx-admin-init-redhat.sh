#!/bin/sh
#
# Nginx Admin control script
#
# chkconfig: - 80 20
# description: Nginx Admin startup script
# processname: nginx-admin
# pidfile: /var/run/nginx-admin/nginx-admin.pid

# Source function library.
. /etc/init.d/functions

NGINX_ADMIN_NAME=$(basename ${0})

if [ -z "$NGINX_ADMIN_PIDFILE" ]; then
	NGINX_ADMIN_PIDFILE=/var/run/${NGINX_ADMIN_NAME}/nginx-admin.pid
fi
export NGINX_ADMIN_PIDFILE

if [ -z "$NGINX_ADMIN_CONSOLE_LOG" ]; then
	NGINX_ADMIN_CONSOLE_LOG=/var/log/${NGINX_ADMIN_NAME}/console.log
fi

if [ -z "$NGINX_ADMIN_LOCKFILE" ]; then
	NGINX_ADMIN_LOCKFILE=/var/lock/subsys/${NGINX_ADMIN_NAME}
fi

prog=${NGINX_ADMIN_NAME}
currenttime=$(date +%s%N | cut -b1-13)

start() {
	echo -n "Starting $prog: "
	if [ -f $NGINX_ADMIN_PIDFILE ]; then
		read ppid < $NGINX_ADMIN_PIDFILE
		if [ `ps --pid $ppid 2> /dev/null | grep -c $ppid 2> /dev/null` -eq '1' ]; then
			echo -n "$prog is already running"
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

	java -jar /usr/share/softwares/nginx-admin-standalone-1.0.0-swarm.jar -dbuser sa -dbpassword sa >> $NGINX_ADMIN_CONSOLE_LOG 2>&1 & echo $! > $NGINX_ADMIN_PIDFILE
	
	touch $NGINX_ADMIN_LOCKFILE
	success
	echo
	return 0
}

stop() {
	echo -n $"Stopping $prog: "
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
			echo "$prog is running (pid $ppid)"
			return 0
		else
			echo "$prog dead but pid file exists"
			return 1
		fi
	fi
	echo "$prog is not running"
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