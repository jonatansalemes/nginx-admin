#!/bin/sh
#
# Nginx agent control script
#
# chkconfig: 2345 80 20
# description: Nginx agent startup/shutdown script
#
### BEGIN INIT INFO
# Provides:             nginx-agent
# Required-Start:       $remote_fs $network
# Required-Stop:        $remote_fs $network
# Should-Start:         $named
# Should-Stop:          $named
# Default-Start:        2 3 4 5
# Default-Stop:         0 1 6
# Short-Description:    Nginx agent
# Description:          Nginx agent startup/shutdown script
### END INIT INFO

# Source function library.
. /etc/init.d/functions
. /opt/nginx-agent/conf/nginx-agent.conf

NGINX_AGENT_NAME=nginx-agent
NGINX_AGENT_PIDFILE=/var/run/$NGINX_AGENT_NAME/nginx-agent.pid
NGINX_AGENT_CONSOLE_LOG=$NGINX_AGENT_LOG/console.log
NGINX_AGENT_LOCKFILE=/var/lock/subsys/$NGINX_AGENT_NAME
STARTUP_WAIT=30
SHUTDOWN_WAIT=30

pid_exists(){
	test -f $NGINX_AGENT_PIDFILE
}

pid() {
    if pid_exists ; then
          echo $(cat $NGINX_AGENT_PIDFILE)
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
	mkdir -p $(dirname $NGINX_AGENT_PIDFILE)
	mkdir -p $(dirname $NGINX_AGENT_CONSOLE_LOG)
	rm -f $NGINX_AGENT_CONSOLE_LOG
	chown $NGINX_AGENT_USER $(dirname $NGINX_AGENT_PIDFILE) || true
	
	runuser $NGINX_AGENT_USER -c "$JAVA -server -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Xms256m -Xmx1g -jar $NGINX_AGENT_BIN/nginx-admin-agent-standalone-$NGINX_AGENT_VERSION-swarm.jar -c $NGINX_AGENT_CONF/nginx-agent.conf" >> $NGINX_AGENT_CONSOLE_LOG 2>&1 & echo $! > $NGINX_AGENT_PIDFILE
	
	if ! is_launched ; then
		rm -f $NGINX_AGENT_PIDFILE
		exit_with_failure "$NGINX_AGENT_NAME not started please see server log on $NGINX_AGENT_CONSOLE_LOG for details and report issue on https://github.com/jslsolucoes/nginx-admin please"
	else
		exit_with_success "$NGINX_AGENT_NAME is started"
	fi
}

start() {
	if is_running ; then
		exit_with_failure "$NGINX_AGENT_NAME is already running"
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
	killproc -p $NGINX_AGENT_PIDFILE -d $SHUTDOWN_WAIT
	children_pids=$(pgrep -P $(pid))
	for child in $children_pids; do
		/bin/kill -9 $child >/dev/null 2>&1
	done
	rm -f $NGINX_AGENT_PIDFILE
	rm -f $NGINX_AGENT_LOCKFILE
}

stop() {
	if is_running ; then
		killpid
		exit_with_success "$NGINX_AGENT_NAME is stopped"
	else 
		exit_with_failure "$NGINX_AGENT_NAME is already stopped"
	fi	
}

status() {
	if is_running ; then
		exit_with_success "$NGINX_AGENT_NAME is running (pid $(pid))" 
	elif is_dead ; then
		exit_with_failure "$NGINX_AGENT_NAME is dead but pid file exists"
	else
		exit_with_failure "$NGINX_AGENT_NAME is not running"
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
