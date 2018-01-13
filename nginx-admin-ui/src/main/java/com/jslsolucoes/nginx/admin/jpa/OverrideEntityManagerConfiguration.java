package com.jslsolucoes.nginx.admin.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import com.jslsolucoes.cdi.misc.annotation.ApplicationProperties;

import br.com.caelum.vraptor.jpa.DefaultEntityManagerConfiguration;

@Specializes
@ApplicationScoped
public class OverrideEntityManagerConfiguration extends DefaultEntityManagerConfiguration {

	@Inject
	@ApplicationProperties
	private Properties properties;
	
	@Override
	public Map<String, Object> properties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return properties;
	}
}
