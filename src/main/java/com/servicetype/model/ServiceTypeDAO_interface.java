package com.servicetype.model;

import java.util.List;

public interface ServiceTypeDAO_interface {
	
	public void insert(ServiceTypeVO svsType);
	public void delete(Integer PK);
	public void update(ServiceTypeVO svsType);
	
	public ServiceTypeVO findByPK (Integer PK);
	public List<ServiceTypeVO> getAll();
	public List<ServiceTypeVO> getSameType(Integer PK);
}
