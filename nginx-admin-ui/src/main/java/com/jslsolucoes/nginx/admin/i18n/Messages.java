package com.jslsolucoes.nginx.admin.i18n;

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