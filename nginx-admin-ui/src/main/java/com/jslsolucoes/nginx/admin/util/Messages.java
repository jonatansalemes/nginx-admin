package com.jslsolucoes.nginx.admin.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	public static String getString(String key) {
		return getString(key, new Object[] {});
	}

	public static String getString(String key, Object... params) {
		try {
			return MessageFormat.format(ResourceBundle.getBundle("messages").getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}