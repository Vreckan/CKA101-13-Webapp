package com.webond.listener;

import com.webond.util.HibernateUtil;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitializerListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Webond context started");
		HibernateUtil.getSessionFactory();
		System.out.println("Hibernate SessionFactory Initialized");
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Webound context ended");
		HibernateUtil.shutdown();
		System.out.println("Hibernate SessionFactory closed");
	}

}
