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

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeUtils;

@RunnerType(OperationalSystemDistribution.CENTOS)
public class CentOsRunner implements Runner {

	
	private Nginx nginx;

	@Override
	public RuntimeResult start() {
		return RuntimeUtils.command(nginx.getBin() + " -c "
				+ nginx.getConf());
	}

	@Override
	public RuntimeResult stop() {
		RuntimeUtils.command(nginx.getBin() + " -s quit");
		RuntimeUtils.command("killall -9 nginx");
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
		return RuntimeUtils.command("ps -p "+nginx.getPid());
	}

	@Override
	public Runner configure(Nginx nginx) {
		this.nginx = nginx;
		return this;
	}
}
