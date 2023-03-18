package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ars_vc.config.HibernateUtil;
import com.ars_vc.entity.Flight;
import com.ars_vc.model.FlightDTO;
import com.ars_vc.service.AirlineService;
import com.ars_vc.service.FlightService;
import com.ars_vc.serviceImpl.AirlineServiceImpl;
import com.ars_vc.serviceImpl.FlightServiceImpl;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightTest {
AirlineService aService = new AirlineServiceImpl();
FlightService flightService = new FlightServiceImpl();
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
//Testing for create flight
@Test
@Order(1)
void testCreateFlight() {
	Transaction tx = session.beginTransaction();
	Flight flight = Flight.builder().avilableSeats(2).totalSeats(200).source("Mumbai").destination("Delhi").travellerClass("Economy").date(LocalDate.of(2023, 03, 18)).build();	
	//count data entries
	Integer i = (Integer)session.save(flight);
	tx.commit();
	assertThat(i>0).isTrue();
}
//Testing for read flight
@Test
@Order(2)
void testReadFlight() {
	FlightDTO fdto = flightService.getFlight(1);
	assertThat(fdto.getDestination()).isEqualTo("Delhi");
}
//Testing for update flight
@Test
@Order(3)
void testUpdateFlight() {
	Transaction tx = session.beginTransaction();
	Flight ft = new Flight();
	ft.setTravellerClass("Business");
	ft.setAvilableSeats(200);
	ft.setSource("Mumbai");
	ft.setDestination("Chennai");
	ft.setDate(LocalDate.of(2023, 03, 18));
	ft.setTime("3:00AM");
	ft.setTotalSeats(200);
	FlightDTO fdto = flightService.updateFlight(1, ft);
	tx.commit();
	assertThat(fdto.getTravellerClass()).isEqualTo("Business");
}
//Testing for delete flight
@Test
@Order(4)
@DisplayName(value="Negative Test Case")
void testDeleteFlight() {
	flightService.deleteFlight(3);
	assertThrows(HibernateException.class, ()->flightService.deleteFlight(3));
}

}
