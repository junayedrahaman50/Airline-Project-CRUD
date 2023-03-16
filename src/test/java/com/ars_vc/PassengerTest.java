package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.ars_vc.entity.Passenger;
import com.ars_vc.model.PassengerDTO;
import com.ars_vc.service.PassengerService;
import com.ars_vc.serviceImpl.PassengerServiceImpl;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PassengerTest {
PassengerService passengerService = new PassengerServiceImpl();
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
//Testing for Passenger registration
@Test
@Order(1)
void testRegisterPassenger() {
	Transaction tx = session.beginTransaction();
	
Passenger passenger = Passenger.builder().name("Junayed").email("junayed@email.com").password("secret").phno("9999999999").username("JN007").role("user").build();
	//count data entries
	Integer i = (Integer)session.save(passenger);
	tx.commit();
	assertThat(i>0).isTrue();
}
//Testing for get passenger
@Test
@Order(2)
void testFetchPassenger() {
	PassengerDTO pdto = passengerService.getPassengerById(5);
	assertThat(pdto.getName()).isEqualTo("Junayed");
}
//Testing update passenger
@Test
@Order(3)
void testUpdatePassenger() {
	Transaction tx = session.beginTransaction();
	Passenger ps = new Passenger();
	ps.setName("Junayed Rahaman");
	ps.setEmail("junayed@gmail.com");
	ps.setPassword("topsecret");
	ps.setPhno("9000000000");
	ps.setUserName("JND007");
	PassengerDTO pdto = passengerService.updatePassenger(5, ps);
	tx.commit();
	assertThat(pdto.getName()).isEqualTo("Junayed Rahaman");
}
//Testing for delete passenger
@Test
@Order(4)
@DisplayName(value="Negative Test Case")
void testDeletePassenger() {
	passengerService.deletePassenger(10);
	assertThrows(HibernateException.class, ()->passengerService.deletePassenger(10));
}


}
