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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.jslsolucoes.nginx.admin.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.os.OperationalSystemInfo;


public class RunnerSelector {

	
	@ApplicationScoped
	@Produces
	public Runner getInstance(){
		OperationalSystemInfo operationalSystemInfo = OperationalSystem.info();
		for(Runner runner : implementations()){
			if(runner.distro().equals(operationalSystemInfo.getOperationalSystemDistribution())){
				return runner;
			}
		}
		return new UnknowDistroRunner();
	}
	
	@SuppressWarnings("serial")
	private List<Runner> implementations(){
		return new ArrayList<Runner>(){{
			add(new CentOsRunner());
			add(new WindowsRunner());
		}};
	}
}
