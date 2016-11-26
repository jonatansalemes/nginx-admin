package com.jslsolucoes.nginx.admin.repository;

import java.io.InputStream;
import java.util.List;

import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface SslCertificateRepository {

	public List<SslCertificate> listAll();

	public OperationType delete(SslCertificate sslCertificate);

	public void upload(InputStream certificate, InputStream key) throws Exception;

	public SslCertificate load(SslCertificate sslCertificate);

}
