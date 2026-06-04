package com.servicetype.model;

import java.util.List;
import java.util.Map;

public interface ServiceTypeDao_interface {
	public void insert(ServiceTypeVO serviceType);
	public void delete(ServiceTypeVO serviceType);
	public void update(ServiceTypeVO serviceType);
    public ServiceTypeVO findByPrimaryKey(Integer serviceTypeId);
	public List<ServiceTypeVO> getAll();
}
