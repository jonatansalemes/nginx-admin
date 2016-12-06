package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Smtp;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;

public interface SmtpRepository {
	public Smtp configuration();

	public OperationResult saveOrUpdate(Smtp smtp);

	public List<String> validateBeforeSaveOrUpdate(Smtp smtp);
}
