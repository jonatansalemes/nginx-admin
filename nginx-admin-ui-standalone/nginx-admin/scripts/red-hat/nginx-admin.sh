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
          echo $(cat $NGINX_ADMIN_PIDFILE)
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
	mkdir -p $(dirname $NGINX_ADMIN_PIDFILE)
	mkdir -p $(dirname $NGINX_ADMIN_CONSOLE_LOG)
	rm -f $NGINX_ADMIN_CONSOLE_LOG
	chown $NGINX_ADMIN_USER $(dirname $NGINX_ADMIN_PIDFILE) || true

	runuser $NGINX_ADMIN_USER -c "$JAVA -server -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Xms256m -Xmx1g -jar $NGINX_ADMIN_BIN/nginx-admin-ui-standalone-$NGINX_ADMIN_VERSION-swarm.jar -c $NGINX_ADMIN_CONF/nginx-admin.conf" >> $NGINX_ADMIN_CONSOLE_LOG 2>&1 & echo $! > $NGINX_ADMIN_PIDFILE
	
	if ! is_launched ; then
		rm -f $NGINX_ADMIN_PIDFILE
		exit_with_failure "$NGINX_ADMIN_NAME not started please see server log on $NGINX_ADMIN_CONSOLE_LOG for details and report issue on https://github.com/jslsolucoes/nginx-admin please"
	else
		exit_with_success "$NGINX_ADMIN_NAME is started"
	fi
}

start() {
	if is_running ; then
		exit_with_failure "$NGINX_ADMIN_NAME is already running"
	else
		try_launch
	fi
}

exit_with_success(){
	echo -n $1
	success
	echo
	return 0
}

exit_with_failure(){
	echo -n $1
	failure
	echo
	return 1
}

killpid() {
	killproc -p $NGINX_ADMIN_PIDFILE -d $SHUTDOWN_WAIT
	children_pids=$(pgrep -P $(pid))
	for child in $children_pids; do
		/bin/kill -9 $child >/dev/null 2>&1
	done
	rm -f $NGINX_ADMIN_PIDFILE
	rm -f $NGINX_ADMIN_LOCKFILE
}

stop() {
	if is_running ; then
		killpid
		exit_with_success "$NGINX_ADMIN_NAME is stopped"
	else 
		exit_with_failure "$NGINX_ADMIN_NAME is already stopped"
	fi	
}

status() {
	if is_running ; then
		exit_with_success "$NGINX_ADMIN_NAME is running (pid $(pid))" 
	elif is_dead ; then
		exit_with_failure "$NGINX_ADMIN_NAME is dead but pid file exists"
	else
		exit_with_failure "$NGINX_ADMIN_NAME is not running"
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
