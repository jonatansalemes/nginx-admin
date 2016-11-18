package com.jslsolucoes.nginx.admin.nginx;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.inject.Vetoed;

import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;
import com.jslsolucoes.nginx.admin.runtime.RuntimeResult;
import com.jslsolucoes.nginx.admin.runtime.RuntimeUtils;

@Vetoed
public class WindowsRunner implements Runner {

	@Override
	public OperationalSystemDistribution distro() {
		return OperationalSystemDistribution.WINDOWS;
	}

	@Override
	public RuntimeResult start(NginxConfiguration nginxConfiguration) {
		return RuntimeUtils.command(
				"cmd.exe /c start /B nginx.exe -c \"" + nginxConfiguration.getConf().getAbsolutePath() + "\"",
				nginxConfiguration.getHome(), false);
	}

	@Override
	public RuntimeResult stop(NginxConfiguration nginxConfiguration) {
		return RuntimeUtils.command("cmd.exe /c nginx.exe -s quit", nginxConfiguration.getHome());
	}

	@Override
	public RuntimeResult restart(NginxConfiguration nginxConfiguration) {
		stop(nginxConfiguration);
		start(nginxConfiguration);
		return status(nginxConfiguration);
	}

	@Override
	public RuntimeResult status(NginxConfiguration nginxConfiguration) {
		return RuntimeUtils.command("tasklist /fi \"imagename eq nginx.exe\"");
	}

	public static void main(String[] args)
			throws InvalidExitValueException, IOException, InterruptedException, TimeoutException, ExecutionException {

		try {
			new ProcessExecutor().commandSplit("cmd.exe /c start /B calc.exe")
					.directory(new File("D:\\softwares\\nginx-1.11.6")).timeout(1, TimeUnit.SECONDS).execute();
		} catch (TimeoutException e) {
			
		}
	}
}
