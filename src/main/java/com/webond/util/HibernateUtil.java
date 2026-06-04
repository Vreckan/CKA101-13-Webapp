package com.webond.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//import com.servicetype.model.ServiceTypeVO;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
//                    .addAnnotatedClass(ServiceTypeVO.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
    	getSessionFactory().close();
    }
}