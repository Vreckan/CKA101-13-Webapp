package com.service.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.webond.util.HibernateUtil;

public class ServiceDAOHibernate implements ServiceDAO_interface {

	private SessionFactory factory;

	public ServiceDAOHibernate() {
		factory = HibernateUtil.getSessionFactory();
	}

	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public void insert(ServiceVO svc) {
		getSession().persist(svc);
	}

	@Override
	public void delete(Integer PK) {
		ServiceVO svc = getSession().find(ServiceVO.class, PK);
		if (svc != null) {
			getSession().remove(svc);
		}
	}

	@Override
	public void update(ServiceVO svc) {
		getSession().merge(svc);
	}

	@Override
	public ServiceVO getOne(Integer PK) {
		return getSession().find(ServiceVO.class, PK);
	}

	@Override
	public List<ServiceVO> getAll() {
		return(List<ServiceVO>) getSession().createQuery("from ServiceVO",ServiceVO.class).getResultList();
	}
	
	public List<ServiceVO> getByServiceTypeId(Integer serviceTypeId) {
	    return getSession()
	            .createQuery(
	            	"from ServiceVO s where s.serviceType.svcTypeID = :serviceTypeId",
	                ServiceVO.class
	            )
	            .setParameter("serviceTypeId", serviceTypeId)
	            .getResultList();
	}

}
