package com.jslsolucoes.nginx.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.jslsolucoes.i18n.Messages;
import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.agent.model.FileObjectBuilder;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.ssl.NginxSslReadResponse;
import com.jslsolucoes.nginx.admin.error.NginxAdminRuntimeException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;
import com.jslsolucoes.tagria.lib.form.FormValidation;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("sslCertificate")
public class SslCertificateController {

	private Result result;
	private SslCertificateRepository sslCertificateRepository;
	private NginxAgentRunner nginxAgentRunner;
	private ResourceIdentifierRepository resourceIdentifierRepository;

	@Deprecated
	public SslCertificateController() {

	}

	@Inject
	public SslCertificateController(Result result, SslCertificateRepository sslCertificateRepository,
			NginxAgentRunner nginxAgentRunner, ResourceIdentifierRepository resourceIdentifierRepository) {
		this.result = result;
		this.sslCertificateRepository = sslCertificateRepository;
		this.nginxAgentRunner = nginxAgentRunner;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Path("list/{idNginx}")
	public void list(Long idNginx) {
		this.result.include("sslCertificateList", sslCertificateRepository.listAllFor(new Nginx(idNginx)));
		this.result.include("nginx", new Nginx(idNginx));
	}

	public void validate(Long id, String commonName, Long idResourceIdentifierCertificate,
			Long idResourceIdentifierCertificatePrivateKey, UploadedFile certificateFile,
			UploadedFile certificatePrivateKeyFile, Long idNginx) {
		this.result.use(Results.json()).from(FormValidation.newBuilder()
				.toUnordenedList(sslCertificateRepository.validateBeforeSaveOrUpdate(new SslCertificate(id, commonName,
						new ResourceIdentifier(idResourceIdentifierCertificate),
						new ResourceIdentifier(idResourceIdentifierCertificatePrivateKey), new Nginx(idNginx)))),
				"errors").serialize();
	}

	@Path("form/{idNginx}")
	public void form(Long idNginx) {
		this.result.include("nginx", new Nginx(idNginx));
	}

	@Path("edit/{idNginx}/{id}")
	public void edit(Long idNginx, Long id) {
		this.result.include("sslCertificate", sslCertificateRepository.load(new SslCertificate(id)));
		this.result.forwardTo(this).form(idNginx);
	}

	@Path("download/{idNginx}/{uuid}")
	public Download download(Long idNginx, String uuid) {
		NginxResponse nginxResponse = nginxAgentRunner.readSsl(idNginx, uuid);
		if (nginxResponse.success()) {
			NginxSslReadResponse nginxSslReadResponse = (NginxSslReadResponse) nginxResponse;
			return new InputStreamDownload(
					new ByteArrayInputStream(nginxSslReadResponse.getFileObject().getContent().getBytes()),
					"application/octet-stream", uuid + ".ssl", true, nginxSslReadResponse.getFileObject().getSize());
		} else {
			throw new NginxAdminRuntimeException(Messages.getString("ssl.download.failed"));
		}
	}

	@Path("delete/{idNginx}/{id}")
	public void delete(Long idNginx, Long id) {
		SslCertificate sslCertificate = sslCertificateRepository.load(new SslCertificate(id));
		NginxResponse nginxResponseForCertificate = nginxAgentRunner.deleteSsl(idNginx,
				sslCertificate.getResourceIdentifierCertificate().getUuid());
		NginxResponse nginxResponseForCertificatePrivateKey = nginxAgentRunner.deleteSsl(idNginx,
				sslCertificate.getResourceIdentifierCertificatePrivateKey().getUuid());
		if (nginxResponseForCertificate.success() && nginxResponseForCertificatePrivateKey.success()) {
			this.result.include("operation", sslCertificateRepository.delete(new SslCertificate(id)));
		} else {
			this.result.include("operation", OperationStatusType.DELETE_FAILED);
		}
		this.result.redirectTo(this).list(idNginx);
	}

	@Post
	public void saveOrUpdate(Long id, String commonName, UploadedFile certificateFile,
			UploadedFile certificatePrivateKeyFile, Long idNginx) throws IOException {

		if (id == null) {
			ResourceIdentifier resourceIdentifierForCertificate = resourceIdentifierRepository.create();
			ResourceIdentifier resourceIdentifierForCertificatePrivateKey = resourceIdentifierRepository.create();

			NginxResponse nginxResponseForCertificate = createOrUpdateSsl(idNginx,
					resourceIdentifierForCertificate.getUuid(), certificateFile.getFile());
			NginxResponse nginxResponseForCertificatePrivateKey = createOrUpdateSsl(idNginx,
					resourceIdentifierForCertificatePrivateKey.getUuid(), certificatePrivateKeyFile.getFile());

			if (nginxResponseForCertificate.success() && nginxResponseForCertificatePrivateKey.success()) {
				OperationResult operationResult = sslCertificateRepository
						.saveOrUpdate(new SslCertificate(id, commonName, resourceIdentifierForCertificate,
								resourceIdentifierForCertificatePrivateKey, new Nginx(idNginx)));
				this.result.include("operation", operationResult.getOperationType());
				this.result.redirectTo(this).edit(idNginx, operationResult.getId());
			} else {
				this.result.include("operation", OperationStatusType.INSERT_FAILED);
				this.result.redirectTo(this).form(idNginx);
			}
		} else {
			SslCertificate sslCertificate = sslCertificateRepository.load(new SslCertificate(id));

			boolean success = true;
			if (certificateFile != null) {
				NginxResponse nginxResponseForCertificate = createOrUpdateSsl(idNginx,
						sslCertificate.getResourceIdentifierCertificate().getUuid(), certificateFile.getFile());
				success = nginxResponseForCertificate.success();
			}

			if (certificatePrivateKeyFile != null) {
				NginxResponse nginxResponseForCertificatePrivateKey = createOrUpdateSsl(idNginx,
						sslCertificate.getResourceIdentifierCertificatePrivateKey().getUuid(),
						certificatePrivateKeyFile.getFile());
				success = nginxResponseForCertificatePrivateKey.success();
			}

			if (success) {
				OperationResult operationResult = sslCertificateRepository.saveOrUpdate(
						new SslCertificate(id, commonName, sslCertificate.getResourceIdentifierCertificate(),
								sslCertificate.getResourceIdentifierCertificatePrivateKey(), new Nginx(idNginx)));
				this.result.include("operation", operationResult.getOperationType());
				this.result.redirectTo(this).edit(idNginx, operationResult.getId());
			} else {
				this.result.include("operation", OperationStatusType.UPDATE_FAILED);
				this.result.redirectTo(this).form(idNginx);
			}

		}
	}

	private NginxResponse createOrUpdateSsl(Long idNginx, String uuid, InputStream inputStream) throws IOException {
		return nginxAgentRunner.createSsl(idNginx, uuid,
				FileObjectBuilder.newBuilder().withSize(Long.valueOf(inputStream.available()))
						.withContent(IOUtils.toString(inputStream, "UTF-8")).withCharset("UTF-8")
						.withName(uuid + ".ssl").build());
	}
}
