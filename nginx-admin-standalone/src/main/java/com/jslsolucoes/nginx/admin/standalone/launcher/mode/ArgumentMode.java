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
package com.jslsolucoes.nginx.admin.standalone.launcher.mode;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.jslsolucoes.nginx.admin.standalone.launcher.Launcher;

public class ArgumentMode implements LauncherMode {

	private String[] args;

	public ArgumentMode(String[] args) {
		this.args = args;
	}

	@Override
	public Launcher launcher() throws Exception {
		Options options = new Options();
	    options.addOption(new Option("help", false, "Help"));
	    options.addOption(new Option("p", true, "Port to manager listening (default 3000)"));
	    options.addOption(new Option("h", true, "Home folder (default user home)"));
		
		CommandLineParser commandLineParser = new DefaultParser();
		CommandLine commandLine = commandLineParser.parse(options, args);
		
		Launcher launch = new Launcher();
		launch.setQuit(false);
		
		if (commandLine.hasOption("help")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("java -jar nginx-admin-standalone-1.0.0-swarm.jar", options);
			launch.setQuit(true);
		}
		
		if(!launch.getQuit()){
			
			if(commandLine.hasOption("h")){
				launch.setHome(commandLine.getOptionValue("h"));
			} else {
				launch.setHome("~");
			}
			
			if(commandLine.hasOption("p")){
				launch.setPort(Integer.valueOf(commandLine.getOptionValue("p")));
			} else {
				launch.setPort(3000);
			}
		}
		
		return launch;
	}

}
