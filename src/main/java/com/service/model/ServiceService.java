package com.service.model;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceService {

	private ServiceDAO_interface dao;

	public ServiceService() {
//		dao = new ServiceDAO();
		dao = new ServiceDAOHibernate();
	}

	public ServiceVO add(Integer serviceTypeId, Integer memberId, String serviceName, String description,
			Integer hourlyRate, Byte status, LocalDateTime createdAt) {

		ServiceVO svc = new ServiceVO();

		svc.setServiceTypeId(serviceTypeId);
		svc.setMemberId(memberId);
		svc.setServiceName(serviceName);
		svc.setDescription(description);
		svc.setHourlyRate(hourlyRate);
		svc.setStatus(status);
		svc.setCreatedAt(createdAt);

		dao.insert(svc);

		return svc;
	}

	public ServiceVO update(Integer serviceId, Integer serviceTypeId, Integer memberId, String serviceName,
			String description, Integer hourlyRate, Byte status) {

		ServiceVO svc = new ServiceVO();

		svc.setServiceId(serviceId);
		svc.setServiceTypeId(serviceTypeId);
		svc.setMemberId(memberId);
		svc.setServiceName(serviceName);
		svc.setDescription(description);
		svc.setHourlyRate(hourlyRate);
		svc.setStatus(status);

		dao.update(svc);

		return svc;
	}

	public void delete(Integer serviceId) {
		dao.delete(serviceId);
	}

	public ServiceVO getOneService(Integer serviceId) {
		return dao.getOne(serviceId);
	}

	public List<ServiceVO> getAll() {
		return dao.getAll();
	}
	
	//關聯查詢
	
	public List<ServiceVO> getServicesByServiceTypeId(Integer serviceTypeId) {
	    return dao.getByServiceTypeId(serviceTypeId);
	}
}