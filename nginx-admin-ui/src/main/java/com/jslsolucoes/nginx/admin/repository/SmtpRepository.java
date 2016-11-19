package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.Smtp;

public interface SmtpRepository {
	public Smtp smtp();

	public Smtp update(Smtp smtp);
}
