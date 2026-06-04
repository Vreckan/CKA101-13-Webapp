package com.service.model;

import java.util.List;

public interface ServiceDAO_interface {
	
	public void insert(ServiceVO svc);
	public void delete(Integer PK);
	public void update(ServiceVO svc);
	
	public ServiceVO getOne(Integer PK);
	public List<ServiceVO> getAll();
	
	//
	List<ServiceVO> getByServiceTypeId(Integer serviceTypeId);
}
