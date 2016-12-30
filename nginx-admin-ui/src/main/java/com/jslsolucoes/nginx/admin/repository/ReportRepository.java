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

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.StatusCodeStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;

public interface ReportRepository {

	public List<UserAgentStatistics> browsers(VirtualHost virtualHost,LocalDate from,LocalTime fromTime,LocalDate to,
			LocalTime toTime);

	public List<OriginStatistics> ips(VirtualHost virtualHost,LocalDate from,LocalTime fromTime,LocalDate to,
			LocalTime toTime);

	public List<StatusCodeStatistics> statuses(VirtualHost virtualHost,LocalDate from,LocalTime fromTime,LocalDate to,
			LocalTime toTime);

	public List<String> validateBeforeSearch(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime);

}
