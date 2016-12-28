package com.jslsolucoes.nginx.admin.repository;

import java.io.IOException;

import org.hibernate.HibernateException;

public interface DatabaseRepository {
		
	public void installOrUpgrade() throws HibernateException, IOException;

	public Boolean installOrUpgradeRequired();

}
