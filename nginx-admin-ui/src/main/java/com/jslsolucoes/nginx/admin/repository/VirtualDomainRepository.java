package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.VirtualDomain;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface VirtualDomainRepository {

	public List<VirtualDomain> listAll();

	public OperationType delete(VirtualDomain virtualDomain);

	public VirtualDomain load(VirtualDomain virtualDomain);

	public OperationResult saveOrUpdate(VirtualDomain virtualDomain);

	public List<String> validateBeforeSaveOrUpdate(VirtualDomain virtualDomain);
}
