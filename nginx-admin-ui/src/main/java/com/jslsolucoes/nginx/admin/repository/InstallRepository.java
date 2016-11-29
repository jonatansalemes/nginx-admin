package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

public interface InstallRepository {

	public List<String> validateBeforeInstall(String login,String loginConfirm,String adminPassword,String adminPasswordConfirm,
			String nginxBin,String nginxSettings,String smtpHost, Integer smtpPort, Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress,
			String smtpUsername, String smtpPassword);
	
	public void install(String login,String loginConfirm,String adminPassword,String adminPasswordConfirm,
			String nginxBin,String nginxSettings,String smtpHost, Integer smtpPort, Integer smtpAuthenticate, Integer smtpTls, String smtpFromAddress,
			String smtpUsername, String smtpPassword);

}
