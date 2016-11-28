/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.nginx;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeUtils;

@RunnerType(OperationalSystemDistribution.WINDOWS)
public class WindowsRunner implements Runner {

	private Nginx nginx;

	@Override
	public RuntimeResult start() {
		RuntimeUtils.command("cmd.exe /c " + executable() + " -c " + nginx.getHome() + File.separator + "settings"
				+ File.separator + "nginx.conf", new File(nginx.getBin()).getParent(), 1);
		return status();
	}

	@Override
	public RuntimeResult stop() {
		RuntimeUtils.command("cmd.exe /c " + executable() + " -s quit", new File(nginx.getBin()).getParent());
		RuntimeUtils.command("taskkill /f /im " + executable() + "");
		return status();
	}

	@Override
	public RuntimeResult restart() {
		stop();
		start();
		return status();
	}

	@Override
	public RuntimeResult status() {
		return RuntimeUtils.command("tasklist /fi \"imagename eq " + executable() + "\"");
	}

	@Override
	public Runner configure(Nginx nginx) {
		this.nginx = nginx;
		return this;
	}

	@Override
	public RuntimeResult testConfig() {
		return RuntimeUtils.command("cmd.exe /c " + executable() + " -t -c " + nginx.getHome() + File.separator
				+ "settings" + File.separator + "nginx.conf", new File(nginx.getBin()).getParent());
	}

	private String executable() {
		return FilenameUtils.getName(nginx.getBin());
	}

}
