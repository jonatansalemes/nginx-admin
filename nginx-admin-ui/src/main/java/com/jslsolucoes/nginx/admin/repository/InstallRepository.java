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
