#!/bin/sh
#
### BEGIN INIT INFO
# Provides:             nginx-agent
# Required-Start:       $remote_fs $network
# Required-Stop:        $remote_fs $network
# Should-Start:         $named
# Should-Stop:          $named
# Default-Start:        2 3 4 5
# Default-Stop:         0 1 6
# Short-Description:    Nginx Agent
# Description:          Provide Nginx Agent startup/shutdown script
### END INIT INFO

. /lib/lsb/init-functions
. /opt/nginx-agent/conf/nginx-agent.conf

NGINX_AGENT_NAME=nginx-agent
NGINX_AGENT_PIDFILE=/var/run/$NGINX_AGENT_NAME/nginx-admin.pid
NGINX_AGENT_CONSOLE_LOG=$NGINX_AGENT_LOG/console.log
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
    pidofproc -p "$NGINX_AGENT_PIDFILE" "java" >/dev/null 2>&1
}

is_dead() {
    pid_exists && ! is_running
}


is_launched() {
        count=0
        until [ $count -gt $STARTUP_WAIT ]
        do
                sleep 1
                count=$((count+1))
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

        start-stop-daemon --start --make-pidfile --pidfile $NGINX_AGENT_PIDFILE --chuid $NGINX_AGENT_USER --user $NGINX_AGENT_USER --chdir $NGINX_AGENT_HOME --exec $JAVA --background -- -server -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Xms256m -Xmx1g -jar $NGINX_AGENT_BIN/nginx-admin-agent-standalone-$NGINX_AGENT_VERSION-swarm.jar -c $NGINX_AGENT_CONF/nginx-agent.conf

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
        log_success_msg $1
        return 0
}

exit_with_failure(){
        log_failure_msg $1
        return 1
}

killpid() {
        start-stop-daemon --stop --quiet --pidfile $NGINX_AGENT_PIDFILE --user $NGINX_AGENT_USER --retry=TERM/$SHUTDOWN_WAIT/KILL/5 >/dev/null 2>&1
        children_pids=$(pgrep -P $(pid))
        for child in $children_pids; do
                /bin/kill -9 $child >/dev/null 2>&1
        done
        rm -f $NGINX_AGENT_PIDFILE
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
		
	log_action_msg "Usage: $0 {start|stop|status|restart}"
	exit 1
	;;
esac
