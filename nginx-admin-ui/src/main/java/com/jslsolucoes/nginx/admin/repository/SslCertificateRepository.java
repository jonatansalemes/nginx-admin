package com.jslsolucoes.nginx.admin.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface SslCertificateRepository {

	public OperationType deleteWithResource(SslCertificate sslCertificate) throws IOException;

	public SslCertificate load(SslCertificate sslCertificate);

	public OperationResult saveOrUpdate(SslCertificate sslCertificate, InputStream certificate, InputStream privateKey)
			throws IOException;

	public InputStream download(String hash) throws FileNotFoundException;

	public List<SslCertificate> listAllFor(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(SslCertificate sslCertificate);
}
