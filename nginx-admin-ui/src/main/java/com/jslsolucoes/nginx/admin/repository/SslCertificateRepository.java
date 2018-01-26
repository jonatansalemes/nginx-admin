package com.jslsolucoes.nginx.admin.repository;

import java.io.InputStream;
import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;

public interface SslCertificateRepository {

	public OperationStatusType delete(SslCertificate sslCertificate) ;

	public SslCertificate load(SslCertificate sslCertificate);

	public OperationResult saveOrUpdate(SslCertificate sslCertificate, InputStream certificate, InputStream privateKey);
	
	public List<SslCertificate> listAllFor(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(SslCertificate sslCertificate);
}
