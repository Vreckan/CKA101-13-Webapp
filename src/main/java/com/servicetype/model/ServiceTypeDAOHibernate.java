package com.servicetype.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.webond.util.HibernateUtil;

public class ServiceTypeDAOHibernate implements ServiceTypeDAO_interface {
	
    @Override
    public void insert(ServiceTypeVO svcType) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(svcType);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(ServiceTypeVO svcType) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(svcType);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Integer PK) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            ServiceTypeVO svcType = session.get(ServiceTypeVO.class, PK);

            if (svcType != null) {
                session.remove(svcType);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public ServiceTypeVO findByPK(Integer PK) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ServiceTypeVO.class, PK);
        }
    }

    @Override
    public List<ServiceTypeVO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from ServiceTypeVO", ServiceTypeVO.class)
                    .getResultList();
        }
    }

	@Override
	public List<ServiceTypeVO> getSameType(Integer PK) {
		// TODO Auto-generated method stub
		return null;
	}
}