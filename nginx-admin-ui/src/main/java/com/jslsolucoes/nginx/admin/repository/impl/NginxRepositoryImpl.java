package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.template.TemplateProcessor;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

import freemarker.template.TemplateException;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	private static Logger logger = LoggerFactory.getLogger(LogRepositoryImpl.class);

	public NginxRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Nginx configuration() {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Nginx> criteriaQuery = criteriaBuilder.createQuery(Nginx.class);
			criteriaQuery.select(criteriaQuery.from(Nginx.class));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<>();

		if (!new File(nginx.getBin()).exists()) {
			errors.add(Messages.getString("nginx.invalid.bin.file", nginx.getBin()));
		}

		File settings = new File(nginx.getSettings());
		if (!canWriteOnFolder(settings)) {
			errors.add(Messages.getString("nginx.invalid.settings.permission", nginx.getSettings()));
		}
		return errors;
	}

	private boolean canWriteOnFolder(File settings) {
		if (!settings.exists()) {
			try {
				FileUtils.forceMkdir(settings);
				return true;
			} catch (IOException exception) {
				logger.error("Could not create on folder", exception);
				return false;
			}
		} else {
			try {
				FileUtils.touch(new File(settings, "touch.txt"));
				return true;
			} catch (IOException exception) {
				logger.error("Could not touch on folder", exception);
				return false;
			}
		}
	}

	@Override
	public OperationResult saveOrUpdateAndConfigure(Nginx nginx) throws NginxAdminException {
		nginx.setSettings(normalize(nginx.getSettings()));
		nginx.setBin(normalize(nginx.getBin()));
		configure(nginx);
		return super.saveOrUpdate(nginx);
	}

	private String normalize(String path) {
		return path.replaceAll("\\\\", "/");
	}

	private void configure(Nginx nginx) throws NginxAdminException {
		try {
			copy(nginx);
			conf(nginx);
			root(nginx);
		} catch (IOException | NginxAdminException | TemplateException e) {
			throw new NginxAdminException(e);
		}
	}

	private void root(Nginx nginx) throws NginxAdminException, IOException, TemplateException {
		TemplateProcessor.build().withTemplate("/template/dynamic/nginx", "root.tpl").withData("nginx", nginx)
				.toLocation(new File(nginx.virtualHost(), "root.conf")).process();
	}

	private void conf(Nginx nginx) throws NginxAdminException, IOException, TemplateException {
		TemplateProcessor.build().withTemplate("/template/dynamic/nginx", "nginx.tpl").withData("nginx", nginx)
				.toLocation(new File(nginx.setting(), "nginx.conf")).process();
	}

	private void copy(Nginx nginx) throws IOException {
		FileUtils.forceMkdir(nginx.setting());
		copyToDirectory(getClass().getResource("/template/fixed/nginx"), nginx.setting(),
				file -> !"tpl".equals(FilenameUtils.getExtension(file.getName())));
	}

	public void copyToDirectory(URL url, File destination, FileFilter fileFilter) throws IOException {
		if ("vfs".equals(url.getProtocol())) {
			copyFromVFS((VirtualFile) url.getContent(), destination, fileFilter);
		} else if ("jar".equals(url.getProtocol())) {
			copyFromJar(url, destination, fileFilter);
		} else {
			copyFromFile(url, destination, fileFilter);
		}
	}

	public void copyFromFile(URL url, File destination, FileFilter fileFilter) throws IOException {
		File source = new File(url.getPath());
		if (source.isDirectory()) {
			org.apache.commons.io.FileUtils.copyDirectory(source, destination, fileFilter);
		} else {
			org.apache.commons.io.FileUtils.copyFileToDirectory(source, destination);
		}
	}

	private void copyFromJar(URL url, File destination, FileFilter fileFilter) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		Enumeration<JarEntry> files = jarURLConnection.getJarFile().entries();
		while (files.hasMoreElements()) {
			JarEntry entry = files.nextElement();
			if (!entry.isDirectory()) {
				File file = new File(destination, entry.getName());
				if (fileFilter.accept(file)) {
					org.apache.commons.io.FileUtils
							.copyInputStreamToFile(jarURLConnection.getJarFile().getInputStream(entry), file);
				}
			} else {
				org.apache.commons.io.FileUtils.forceMkdir(new File(destination, entry.getName()));
			}
		}
	}

	private static void copyFromVFS(VirtualFile virtualRootFile, File dest, FileFilter fileFilter) throws IOException {
		for (VirtualFile virtualFile : virtualRootFile.getChildren()) {
			String fileName = virtualFile.getName();
			if (!virtualFile.isDirectory()) {
				File file = new File(dest, fileName);
				if (fileFilter.accept(file)) {
					FileUtils.copyInputStreamToFile(virtualFile.openStream(), file);
				}
			} else {
				File created = new File(dest, fileName);
				FileUtils.forceMkdir(created);
				copyFromVFS(virtualFile, created, fileFilter);
			}
		}
	}
}
