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
package com.jslsolucoes.nginx.admin.html;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jslsolucoes.tagria.lib.html.Li;
import com.jslsolucoes.tagria.lib.html.Ul;

public class HtmlUtil {

	private HtmlUtil() {

	}

	public static String convertToUnodernedList(List<String> items) {
		if (CollectionUtils.isEmpty(items)) {
			return "";
		} else {
			Ul ul = new Ul();
			for (String item : items) {
				ul.add(new Li().add(item));
			}
			return ul.getHtml();
		}
	}
}
