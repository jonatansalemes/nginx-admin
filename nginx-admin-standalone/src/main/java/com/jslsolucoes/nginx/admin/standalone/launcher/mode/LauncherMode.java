package com.jslsolucoes.nginx.admin.standalone.launcher.mode;

import com.jslsolucoes.nginx.admin.standalone.launcher.Launcher;

public interface LauncherMode {
	public Launcher launcher() throws Exception;
}
