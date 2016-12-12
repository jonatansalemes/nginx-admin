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
NGINX_ADMIN_PIDFILE=/var/run/$NGINX_ADMIN_NAME/nginx-admin.pid
NGINX_ADMIN_CONSOLE_LOG=$NGINX_ADMIN_LOG/console.log
NGINX_ADMIN_LOCKFILE=/var/lock/subsys/$NGINX_ADMIN_NAME
STARTUP_WAIT=30
SHUTDOWN_WAIT=30

pid_exists(){
	test -f $NGINX_ADMIN_PIDFILE
}

pid() {
    if pid_exists ; then
          read ppid < $NGINX_ADMIN_PIDFILE
          echo $ppid
    else
          echo 0
    fi
    return 0
}

is_running() {
    checkpid $(pid)
}

is_dead() {
    pid_exists && ! is_running
}


is_launched() {
	count=0
	until [ $count -gt $STARTUP_WAIT ]
	do
		sleep 1
		let count=$count+1;
		if is_running ; then
			return 0
		fi	
	done
	return 1
}

try_launch() {
	rm -f $NGINX_ADMIN_CONSOLE_LOG
	cat /dev/null > $NGINX_ADMIN_CONSOLE_LOG
	mkdir -p $(dirname $NGINX_ADMIN_PIDFILE)
	chown $NGINX_ADMIN_USER $(dirname $NGINX_ADMIN_PIDFILE) || true

	su -s /bin/bash $NGINX_ADMIN_USER -c "java -jar $NGINX_ADMIN_BIN/nginx-admin-standalone-$NGINX_ADMIN_VERSION-swarm.jar -p $NGINX_ADMIN_PORT -h $NGINX_ADMIN_HOME" >> $NGINX_ADMIN_CONSOLE_LOG 2>&1 & echo $! > $NGINX_ADMIN_PIDFILE
	
	if ! is_launched ; then
		printf "$NGINX_ADMIN_NAME not started please see server log on $NGINX_ADMIN_CONSOLE_LOG for details and report issue on https://github.com/jslsolucoes/nginx-admin please"
		rm -f $NGINX_ADMIN_PIDFILE
		exit_with_failure
	else
		printf "$NGINX_ADMIN_NAME is started"
	    touch $NGINX_ADMIN_LOCKFILE
		exit_with_success
	fi
}

start() {
	if is_running ; then
		printf "$NGINX_ADMIN_NAME is already running"
		exit_with_failure
	else
		try_launch
	fi
}

exit_with_success(){
	success
	echo
	return 0
}

exit_with_failure(){
	failure
	echo
	return 1
}

killpid() {
	killproc -p $NGINX_ADMIN_PIDFILE -d $SHUTDOWN_WAIT
	rm -f $NGINX_ADMIN_PIDFILE
	rm -f $NGINX_ADMIN_LOCKFILE
}

stop() {
	if is_running ; then
		killpid
		printf "$NGINX_ADMIN_NAME is stopped"
		exit_with_success
	else 
		printf "$NGINX_ADMIN_NAME is already stopped"
		exit_with_failure
	fi	
}

status() {
	if is_running ; then
		printf "$NGINX_ADMIN_NAME is running (pid %s)" $(pid)
		exit_with_success
	elif is_dead ; then
		printf "$NGINX_ADMIN_NAME is dead but pid file exists"
		exit_with_failure
	else
		printf "$NGINX_ADMIN_NAME is not running"
		exit_with_failure
	fi
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