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
package com.jslsolucoes.nginx.admin.scheduler.task;

import org.apache.http.HttpStatus;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.http.HttpClientBuilder;

public class RotateLogTask implements Job {

	private static final Logger logger = LoggerFactory.getLogger(RotateLogTask.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		HttpClientBuilder.build().onError(exception -> logger.error("Error on calling task ", exception)).client()
				.get(jobExecutionContext.getMergedJobDataMap().getString("urlBase") + "/task/rotate/log")
				.onNotStatus(HttpStatus.SC_OK,
						closeableHttpResponse -> logger.error("Job cannot be executed : status code => "
								+ closeableHttpResponse.getStatusLine().getStatusCode()))
				.close();
	}

}
