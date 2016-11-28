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

public class NginxConfiguration {

	private File bin;
	private File conf;
	private File home;

	public NginxConfiguration(File bin, File conf, File home) {
		this.bin = bin;
		this.conf = conf;
		this.home = home;
	}

	public File getBin() {
		return bin;
	}

	public void setBin(File bin) {
		this.bin = bin;
	}

	public File getConf() {
		return conf;
	}

	public void setConf(File conf) {
		this.conf = conf;
	}

	public File getHome() {
		return home;
	}

	public void setHome(File home) {
		this.home = home;
	}
}
