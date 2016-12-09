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
package com.jslsolucoes.nginx.admin.nginx.runner.impl;

import org.apache.commons.io.FilenameUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.nginx.runner.RunnerType;
import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResultType;
import com.jslsolucoes.nginx.admin.runtime.RuntimeUtils;

@RunnerType(OperationalSystemDistribution.WINDOWS)
public class WindowsRunner implements Runner {

	private Nginx nginx;

	@Override
	public RuntimeResult start() {
		RuntimeUtils.command("cmd.exe /c " + executable() + " -c " + nginx.conf().getAbsolutePath(),
				nginx.binFolder(), 1);
		return status();
	}

	@Override
	public RuntimeResult stop() {
		RuntimeUtils.command("cmd.exe /c " + executable() + " -s quit", nginx.binFolder());
		RuntimeUtils.command("taskkill /f /im " + executable());
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
		RuntimeResult runtimeResult = RuntimeUtils.command("tasklist /fi \"imagename eq " + executable() + "\"");
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			if(runtimeResult.getOutput().contains(executable())){
				return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("running"));
			} else {
				return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("stopped"));
			}
		}
		return runtimeResult; 
	}

	@Override
	public Runner configure(Nginx nginx) {
		this.nginx = nginx;
		return this;
	}

	@Override
	public RuntimeResult testConfig() {
		RuntimeResult runtimeResult = RuntimeUtils.command("cmd.exe /c " + executable() + " -t -c " + nginx.conf().getAbsolutePath(),
				nginx.binFolder());
		if(runtimeResult.getRuntimeResultType().equals(RuntimeResultType.SUCCESS)){
			if(runtimeResult.getOutput().contains("syntax is ok")){
				return new RuntimeResult(RuntimeResultType.SUCCESS, Messages.getString("syntax.ok"));
			} else {
				return new RuntimeResult(RuntimeResultType.ERROR, runtimeResult.getOutput());
			}
		}
		return runtimeResult; 
	}

	private String executable() {
		return FilenameUtils.getName(nginx.getBin());
	}

	@Override
	public RuntimeResult version() {
		return RuntimeUtils.command("cmd.exe /c " + executable() + " -v ",
				nginx.binFolder());
	}

	@Override
	public RuntimeResult reload() {
		return RuntimeUtils.command("cmd.exe /c " + executable() + " -s reload -c " + nginx.conf().getAbsolutePath(), nginx.binFolder());
	}

}
