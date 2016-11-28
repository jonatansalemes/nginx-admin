/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.runtime;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

public class RuntimeUtils {

	public static RuntimeResult command(String command) {
		return command(command, null, null, null);
	}

	public static RuntimeResult command(String command, String directory) {
		return command(command, null, directory, null);
	}

	public static RuntimeResult command(String command, String directory, Integer timeout) {
		return command(command, null, directory, timeout);
	}

	public static RuntimeResult command(String command, Map<String, String> enviroment, String directory,
			Integer timeout) {

		System.out.println(command);

		try {
			ProcessExecutor processExecutor = new ProcessExecutor();
			processExecutor.commandSplit(command);
			processExecutor.readOutput(true);
			if (directory != null) {
				processExecutor.directory(new File(directory));
			}
			if (enviroment != null) {
				processExecutor.environment(enviroment);
			}
			if (timeout != null) {
				processExecutor.timeout(timeout, TimeUnit.SECONDS);
			}
			ProcessResult processResult = processExecutor.execute();
			String output = processResult.outputUTF8().replaceAll("\n", "<br/>");

			if (processResult.getExitValue() == 0) {
				return new RuntimeResult(RuntimeResultType.SUCCESS, output);
			} else {
				return new RuntimeResult(RuntimeResultType.ERROR, output);
			}
		} catch (Exception exception) {
			return new RuntimeResult(RuntimeResultType.ERROR,
					ExceptionUtils.getFullStackTrace(exception).replaceAll("\n", "<br/>"));
		}
	}
}
