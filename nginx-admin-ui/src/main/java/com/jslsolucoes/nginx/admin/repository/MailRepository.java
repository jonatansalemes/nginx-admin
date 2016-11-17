package com.jslsolucoes.nginx.admin.repository;

import java.util.concurrent.Future;

public interface MailRepository {

	public Future<Void> send(String subject,String to,String message);
	
}
