package com.ars_vc.config;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.ars_vc.entity.Admin;
import com.ars_vc.entity.Airline;
import com.ars_vc.entity.Flight;
import com.ars_vc.entity.Passenger;
import com.ars_vc.entity.TicketBooking;
import com.ars_vc.entity.User;

public class HibernateUtil {
	//reference variable of session factory interface
	private static SessionFactory sessionFactory;
	//method of return type SessionFactory used for connection
	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory==null) {
			//reference variable of Configuration class
			try{
				Configuration configuration=new Configuration();
				//object of properties class
				Properties settings=new Properties();
				settings.put(Environment.DRIVER,"com.mysql.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/projects");
				settings.put(Environment.USER,"root");
				settings.put(Environment.PASS,"root");
				settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");
				settings.put(Environment.SHOW_SQL,"true");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				//using thread for concurrent execution
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
				settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS,"true");
				//passing settings to set properties
				configuration.setProperties(settings);
				
				//mapping classes
			configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Admin.class);
			configuration.addAnnotatedClass(Passenger.class);
			configuration.addAnnotatedClass(Flight.class);
			configuration.addAnnotatedClass(Airline.class);
			configuration.addAnnotatedClass(TicketBooking.class);
				
		ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		System.out.println("hibernate java config serviceRegistry created");
		sessionFactory=configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
			}catch(HibernateException e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		//return sessionFactory
		return sessionFactory;
	}
	public static Session getSession()
	{
		return getSessionFactory().openSession();
	}

}
