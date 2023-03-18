package com.ars_vc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import com.ars_vc.config.HibernateUtil;
import com.ars_vc.entity.Airline;
import com.ars_vc.model.AirlineDTO;
import com.ars_vc.service.AirlineService;
import com.ars_vc.serviceImpl.AirlineServiceImpl;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AirlineTest {
AirlineService airlineService = new AirlineServiceImpl();
public static SessionFactory sessionFactory;
private Session session;
@BeforeAll
//call session factory
static void setUp() {
	sessionFactory = HibernateUtil.getSessionFactory();
}
//open session before testing each method
@BeforeEach
void openSession() {
	session = sessionFactory.openSession();
}
//close session
@AfterEach
void closeSession() {
	if(session != null) 
		session.close();
	System.out.println("session closed!");

}
//close session factory
@AfterAll
static void tearDown() {
	if(sessionFactory != null) 
		sessionFactory.close();
	System.out.println("Session Factory Closed!");
	
}
//Testing create Airline
@Test
@Order(1)
void testAddAirline() {
	Transaction tx = session.beginTransaction();
	Airline airline = Airline.builder().airlineName("Air India").fare(3000).build();
	//count data entries
	Integer i = (Integer)session.save(airline);
	tx.commit();
	assertThat(i>0).isTrue();
}
//Testing for read airline
@Test
@Order(2)
void testReadAirline() {
	AirlineDTO adto = airlineService.getAirlineByName("Vistara");
	assertThat(adto.getAirlineName()).isEqualTo("Vistara");
}
//Testing for update airline
@Test
@Order(3)
void testUpdateAirline() {
	Transaction tx = session.beginTransaction();
	Airline al = new Airline();
	al.setAirlineName("Fly Emirates");
	al.setFare(5000);
	AirlineDTO adto = airlineService.updateAirlineById(1, al);
	tx.commit();
	assertThat(adto.getAirlineName()).isEqualTo("Fly Emirates");
}
//Testing for delete airline
@Test
@Order(4)
@DisplayName(value="Negative Test Case")
void testDeleteAirline() {
	airlineService.deleteAirline(10);
	assertThrows(HibernateException.class, ()->airlineService.deleteAirline(10));
}

}
