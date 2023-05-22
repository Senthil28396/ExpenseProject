package com.training.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.training.model.Student;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory buildSessionFactory() {
	Configuration configuration=new Configuration();
	configuration.configure("hibernate.cfg.xml");
	configuration.addAnnotatedClass(Student.class);
	System.out.println("hibernate loaded successfully");

	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
	SessionFactory sessionFactory=configuration.buildSessionFactory(serviceRegistry);
	return sessionFactory;
	}
	public static SessionFactory getSessionFactory() 
	{
	if(sessionFactory==null) sessionFactory=buildSessionFactory();
		return sessionFactory;
	}
}
