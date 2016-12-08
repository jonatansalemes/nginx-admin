#!/bin/sh
#
# /etc/init.d/nginx-admin -- startup script for Nginx admin
#
#
### BEGIN INIT INFO
# Provides:             nginx-admin
# Required-Start:       $remote_fs $network
# Required-Stop:        $remote_fs $network
# Should-Start:         $named
# Should-Stop:          $named
# Default-Start:        2 3 4 5
# Default-Stop:         0 1 6
# Short-Description:    Nginx Admin
# Description:          Provide Nginx Admin startup/shutdown script
### END INIT INFO

NAME=nginx-admin
DESC="Nginx Admin"
DEFAULT="/etc/default/$NAME"

if [ `id -u` -ne 0 ]; then
	echo "You need root privileges to run this script"
	exit 1
fi

if [ -r /etc/default/locale ]; then
	. /etc/default/locale
	export LANG
fi

. /lib/lsb/init-functions

if [ -r /etc/default/rcS ]; then
	. /etc/default/rcS
fi

# Overwrite settings from default file
if [ -f "$DEFAULT" ]; then
	. "$DEFAULT"
fi

# Location of JDK
if [ -n "$JAVA_HOME" ]; then
	export JAVA_HOME
fi

# Setup the JVM
if [ -z "$JAVA" ]; then
	if [ -n "$JAVA_HOME" ]; then
		JAVA="$JAVA_HOME/bin/java"
	else
		JAVA="java"
	fi
fi

# Location of wildfly
if [ -z "$NGINX_ADMIN_HOME" ]; then
	NGINX_ADMIN_HOME="/opt/wildfly"
fi
export NGINX_ADMIN_HOME

# Check if wildfly is installed
if [ ! -f "$NGINX_ADMIN_HOME/jboss-modules.jar" ]; then
	log_failure_msg "$NAME is not installed in \"$NGINX_ADMIN_HOME\""
	exit 1
fi

# Run as wildfly user
# Example of user creation for Debian based:
# adduser --system --group --no-create-home --home $NGINX_ADMIN_HOME --disabled-login wildfly
if [ -z "$NGINX_ADMIN_USER" ]; then
	NGINX_ADMIN_USER=wildfly
fi

# Check wildfly user
id $NGINX_ADMIN_USER > /dev/null 2>&1
if [ $? -ne 0 -o -z "$NGINX_ADMIN_USER" ]; then
	log_failure_msg "User \"$NGINX_ADMIN_USER\" does not exist..."
	exit 1
fi

# Check owner of NGINX_ADMIN_HOME
if [ ! $(stat -L -c "%U" "$NGINX_ADMIN_HOME") = $NGINX_ADMIN_USER ]; then
	log_failure_msg "The user \"$NGINX_ADMIN_USER\" is not owner of \"$NGINX_ADMIN_HOME\""
	exit 1
fi

# Startup mode of wildfly
if [ -z "$NGINX_ADMIN_MODE" ]; then
	NGINX_ADMIN_MODE=standalone
fi

# Startup mode script
if [ "$NGINX_ADMIN_MODE" = "standalone" ]; then
	NGINX_ADMIN_SCRIPT="$NGINX_ADMIN_HOME/bin/standalone.sh"
	if [ -z "$NGINX_ADMIN_CONFIG" ]; then
		NGINX_ADMIN_CONFIG=standalone.xml
	fi
	NGINX_ADMIN_MARKERFILE="$NGINX_ADMIN_HOME/standalone/tmp/startup-marker"
else
	NGINX_ADMIN_SCRIPT="$NGINX_ADMIN_HOME/bin/domain.sh"
	if [ -z "$NGINX_ADMIN_DOMAIN_CONFIG" ]; then
		NGINX_ADMIN_DOMAIN_CONFIG=domain.xml
	fi
	if [ -z "$NGINX_ADMIN_HOST_CONFIG" ]; then
		NGINX_ADMIN_HOST_CONFIG=host.xml
	fi
	NGINX_ADMIN_MARKERFILE="$NGINX_ADMIN_HOME/domain/tmp/startup-marker"
fi

# Check startup file
if [ ! -x "$NGINX_ADMIN_SCRIPT" ]; then
	log_failure_msg "$NGINX_ADMIN_SCRIPT is not an executable!"
	exit 1
fi

# Check cli file
NGINX_ADMIN_CLI="$NGINX_ADMIN_HOME/bin/jboss-cli.sh"
if [ ! -x "$NGINX_ADMIN_CLI" ]; then
	log_failure_msg "$NGINX_ADMIN_CLI is not an executable!"
	exit 1
fi

# The amount of time to wait for startup
if [ -z "$STARTUP_WAIT" ]; then
	STARTUP_WAIT=30
fi

# The amount of time to wait for shutdown
if [ -z "$SHUTDOWN_WAIT" ]; then
	SHUTDOWN_WAIT=30
fi

# Location to keep the console log
if [ -z "$NGINX_ADMIN_CONSOLE_LOG" ]; then
	NGINX_ADMIN_CONSOLE_LOG="/var/log/$NAME/console.log"
fi
export NGINX_ADMIN_CONSOLE_LOG

# Location to set the pid file
NGINX_ADMIN_PIDFILE="/var/run/$NAME/$NAME.pid"
export NGINX_ADMIN_PIDFILE

# Launch wildfly in background
LAUNCH_NGINX_ADMIN_IN_BACKGROUND=1
export LAUNCH_NGINX_ADMIN_IN_BACKGROUND

# Helper function to check status of wildfly service
check_status() {
	pidofproc -p "$NGINX_ADMIN_PIDFILE" "$JAVA" >/dev/null 2>&1
}

case "$1" in
 start)
	log_daemon_msg "Starting $DESC" "$NAME"
	check_status
	status_start=$?
	if [ $status_start -eq 3 ]; then
		mkdir -p $(dirname "$NGINX_ADMIN_PIDFILE")
		mkdir -p $(dirname "$NGINX_ADMIN_CONSOLE_LOG")
		chown $NGINX_ADMIN_USER $(dirname "$NGINX_ADMIN_PIDFILE") || true
		cat /dev/null > "$NGINX_ADMIN_CONSOLE_LOG"
		currenttime=$(date +%s%N | cut -b1-13)

		if [ "$NGINX_ADMIN_MODE" = "standalone" ]; then
			start-stop-daemon --start --user "$NGINX_ADMIN_USER" \
			--chuid "$NGINX_ADMIN_USER" --chdir "$NGINX_ADMIN_HOME" --pidfile "$NGINX_ADMIN_PIDFILE" \
			--exec "$NGINX_ADMIN_SCRIPT" -- -c $NGINX_ADMIN_CONFIG $NGINX_ADMIN_OPTS >> "$NGINX_ADMIN_CONSOLE_LOG" 2>&1 &
		else
			start-stop-daemon --start --user "$NGINX_ADMIN_USER" \
			--chuid "$NGINX_ADMIN_USER" --chdir "$NGINX_ADMIN_HOME" --pidfile "$NGINX_ADMIN_PIDFILE" \
			--exec "$NGINX_ADMIN_SCRIPT" -- --domain-config=$NGINX_ADMIN_DOMAIN_CONFIG \
			--host-config=$NGINX_ADMIN_HOST_CONFIG $NGINX_ADMIN_OPTS >> "$NGINX_ADMIN_CONSOLE_LOG" 2>&1 &
		fi

		count=0
		launched=0
		until [ $count -gt $STARTUP_WAIT ]
		do
			sleep 1
			count=$((count + 1));
			if [ -f "$NGINX_ADMIN_MARKERFILE" ]; then
				markerfiletimestamp=$(grep -o '[0-9]*' "$NGINX_ADMIN_MARKERFILE") > /dev/null
				if [ "$markerfiletimestamp" -gt "$currenttime" ] ; then
					grep -i 'success:' "$NGINX_ADMIN_MARKERFILE" > /dev/null
					if [ $? -eq 0 ] ; then
						launched=1
						break
					fi
				fi
			fi
		done
		if check_status; then
			log_end_msg 0
		else
			log_end_msg 1
		fi

		if [ $launched -eq 0 ]; then
			log_warning_msg "$DESC hasn't started within the timeout allowed"
			log_warning_msg "please review file \"$NGINX_ADMIN_CONSOLE_LOG\" to see the status of the service"
		fi
	elif [ $status_start -eq 1 ]; then
		log_failure_msg "$DESC is not running but the pid file exists"
		exit 1
	elif [ $status_start -eq 0 ]; then
		log_success_msg "$DESC (already running)"
	fi
 ;;
 stop)
	check_status
	status_stop=$?
	if [ $status_stop -eq 0 ]; then
		read kpid < "$NGINX_ADMIN_PIDFILE"
		log_daemon_msg "Stopping $DESC" "$NAME"
		
		children_pids=$(pgrep -P $kpid)

		start-stop-daemon --stop --quiet --pidfile "$NGINX_ADMIN_PIDFILE" \
		--user "$NGINX_ADMIN_USER" --retry=TERM/$SHUTDOWN_WAIT/KILL/5 \
		>/dev/null 2>&1
		
		if [ $? -eq 2 ]; then
			log_failure_msg "$DESC can't be stopped"
			exit 1
		fi
		
		for child in $children_pids; do
			/bin/kill -9 $child >/dev/null 2>&1
		done
		
		log_end_msg 0
	elif [ $status_stop -eq 1 ]; then
		log_action_msg "$DESC is not running but the pid file exists, cleaning up"
		rm -f $NGINX_ADMIN_PIDFILE
	elif [ $status_stop -eq 3 ]; then
		log_action_msg "$DESC is not running"
	fi
 ;;
 restart)
	check_status
	status_restart=$?
	if [ $status_restart -eq 0 ]; then
		$0 stop
	fi
	$0 start
 ;;
 status)
	check_status
	status=$?
	if [ $status -eq 0 ]; then
		read pid < $NGINX_ADMIN_PIDFILE
		log_action_msg "$DESC is running with pid $pid"
		exit 0
	elif [ $status -eq 1 ]; then
		log_action_msg "$DESC is not running and the pid file exists"
		exit 1
	elif [ $status -eq 3 ]; then
		log_action_msg "$DESC is not running"
		exit 3
	else
		log_action_msg "Unable to determine $NAME status"
		exit 4
	fi
 ;;
 *)
 log_action_msg "Usage: $0 {start|stop|restart|status}"
 exit 2
 ;;
esac

exit 0
