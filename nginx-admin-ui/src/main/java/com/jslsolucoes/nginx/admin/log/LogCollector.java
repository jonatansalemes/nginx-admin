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
package com.jslsolucoes.nginx.admin.log;

import org.quartz.JobExecutionContext;
import org.quartz.SimpleScheduleBuilder;

public class LogCollector implements CronTask {
	
	

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
    	System.out.println("executing ..." + jobExecutionContext.getMergedJobDataMap().get("connection"));
    }
    
    @Override
    public SimpleScheduleBuilder frequency() {
        return SimpleScheduleBuilder.simpleSchedule()
	              .withIntervalInSeconds(5)
	              .repeatForever();
    }
}