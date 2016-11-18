package com.jslsolucoes.nginx.admin.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jslsolucoes.tagria.lib.html.Li;
import com.jslsolucoes.tagria.lib.html.Ul;

public class HtmlUtil {
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
