package com.webond.listener;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.webond.util.HibernateUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = { "/service/*" })
public class OpenSessionInViewFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction tx = null;

        try {
            System.out.println("filter open transaction");

            session = factory.getCurrentSession();
            tx = session.beginTransaction();

            chain.doFilter(req, res);

            if (tx != null && tx.isActive()) {
                tx.commit();
                System.out.println("filter commit transaction");
            }

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
                System.out.println("filter rollback transaction");
            }

            e.printStackTrace();

            if (e instanceof ServletException) {
                throw (ServletException) e;
            }

            if (e instanceof IOException) {
                throw (IOException) e;
            }

            throw new ServletException(e);
        }
    }
}