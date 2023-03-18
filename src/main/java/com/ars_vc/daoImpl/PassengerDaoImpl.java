package com.ars_vc.daoImpl;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ars_vc.config.HibernateUtil;
import com.ars_vc.dao.PassengerDao;

import com.ars_vc.entity.Passenger;

public class PassengerDaoImpl implements PassengerDao{
//method to create passenger in DAO layer
	@Override
	public void savePassenger(Passenger passenger) {
		try(Session session=HibernateUtil.getSession())
		{
			session.beginTransaction();
			session.save(passenger);
			session.getTransaction().commit();
			session.clear();
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
//method for login 
	@Override
	public boolean login(String userName, String password) {
		Session session=HibernateUtil.getSession();
		Passenger p=(Passenger)session.get(Passenger.class,Integer.parseInt(JOptionPane.showInputDialog("Enter id: ","type here")));
		if(p.getUserName().equals(userName)&& p.getPassword().equals(password))
		return true;
		else
		return false;
	}
//method to fetch passenger by id in DAO layer
	@Override
	public Passenger getPassenger(int id) {
		try(Session session=HibernateUtil.getSession())
		{
			Passenger passenger=(Passenger)session.get(Passenger
					.class, id);
			return passenger;
		}catch (HibernateException e) {
			System.out.println(e);
		}
		
		return null;
	}
//method to update passenger in DAO layer
	@Override
	public Passenger updatePassenger(int id, Passenger passenger) {
		//We use load when we know data exists
				//update passenger
		try(Session session=HibernateUtil.getSession())
		{
			Passenger pass=(Passenger)session.load(Passenger.class, id);
			pass.setName(passenger.getName());
			pass.setEmail(passenger.getEmail());
			pass.setPhno(passenger.getPhno());
			pass.setUserName(passenger.getUserName());
			pass.setPassword(passenger.getPassword());
			
			session.beginTransaction();
			//save and update fetched data
			//pass contains the updated values
			session.saveOrUpdate(pass);
			//commit
			session.getTransaction().commit();
			return pass;
			
		}catch (HibernateException e) {
			System.out.println(e);
		}
		return null;
	}
//method to delete passenger in DAO layer
	@Override
	public void deletePassenger(int id) throws PersistenceException {
		try(Session session=HibernateUtil.getSession())
		{
			Passenger passn=session.load(Passenger.class,id);
			session.beginTransaction();
			//Delete fetched passenger
			session.delete(passn);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.out.println(e);
		}
		
	}
//method to fetch passenger using email in DAO layer
	@Override
	public Passenger getPassengerByEmail(String email) {
		try(Session session=HibernateUtil.getSession())
		{
			String query="from Passenger p where p.email=:e";
			Query q=session.createQuery(query);
			q.setParameter("e", email);
			Passenger p=(Passenger)q.uniqueResult();
			return p;
		}catch (HibernateException e) {
			System.out.println(e);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	

}
