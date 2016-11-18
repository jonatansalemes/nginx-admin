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
