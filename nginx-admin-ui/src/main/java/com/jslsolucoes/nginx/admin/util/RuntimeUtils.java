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
package com.jslsolucoes.nginx.admin.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

public class RuntimeUtils {

	public static RuntimeResult command(String command) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
			process.waitFor();
			String success = IOUtils.toString(process.getInputStream(), "UTF-8");
			String error = IOUtils.toString(process.getErrorStream(), "UTF-8");
			if (!StringUtils.isEmpty(success)) {
				return new RuntimeResult(RuntimeResultType.SUCCESS, success);
			} else {
				return new RuntimeResult(RuntimeResultType.ERROR, error);
			}
		} catch (Exception exception) {
			return new RuntimeResult(RuntimeResultType.ERROR, ExceptionUtils.getFullStackTrace(exception).replaceAll("\n", "<br/>"));
		}
	}
}
