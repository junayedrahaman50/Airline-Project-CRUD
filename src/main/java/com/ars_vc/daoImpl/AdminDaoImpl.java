package com.ars_vc.daoImpl;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ars_vc.config.HibernateUtil;
import com.ars_vc.dao.AdminDao;
import com.ars_vc.entity.Admin;

public class AdminDaoImpl implements AdminDao{

	@Override
	//create admin in db
	public void registerAdmin(Admin admin) {
		Session session=HibernateUtil.getSession();
		session.beginTransaction();
		session.save(admin);
		session.getTransaction().commit();
		
		session.close();
		
	}
//login admin
	@Override
	public boolean loginAdmin(String userName, String password) {
		Session session=HibernateUtil.getSession();
		//Fetch Data
		Admin admin=(Admin)session.get(Admin.class,Integer.parseInt(JOptionPane.showInputDialog("Enter id: ","type here")));
		if(admin.getUserName().equals(userName)&& admin.getPassword().equals(password))
		return true;
		else
		return false;
	}
	//method for get admin in DAO layer
			@Override
			public Admin getAdminById(int id) {
				//Fetch admin data
				try(Session session=HibernateUtil.getSession()){
					Admin admin=(Admin)session.get(Admin.class, id);
					
					return admin;
					
					}
					
				catch (HibernateException e) {
					System.out.println("hibernate exception is: "+ e);
				}
					
				catch (Exception e) {
					System.out.println("exception is: "+ e);
				}
					return null;
			}
			//method for delete admin in DAO layer
			@Override
			public void deleteAdmin(int id) throws PersistenceException {
				try(Session session=HibernateUtil.getSession()){
					Admin ad=session.load(Admin.class, id);
					
					session.beginTransaction();
					int input=JOptionPane.showConfirmDialog(null, "Do you want to delete?",
							"select what you want to delete or not?",JOptionPane.YES_NO_OPTION);
						
					if(input==0)
					{
						session.delete(ad);
					}
					else
					JOptionPane.showMessageDialog(null, "User wants to retain it!!!");
					session.getTransaction().commit();
					
					}catch (HibernateException e) {
						System.out.println("hibernate exception is: "+ e);
					}
						
					catch (PersistenceException e) {
						System.out.println("You cannot delete your account as you have a booking with us.");
					}
			}
			//method for Update admin  in DAO layer
			@Override
			public Admin updateAdmin(int id, Admin admin) {
				//We use load when we know data exists
				//update admin
				try(Session session=HibernateUtil.getSession()){
					Admin adm=(Admin)session.load(Admin.class, id);
					
					//update existing details with new one
					adm.setAName(admin.getAName());
					adm.setEmail(admin.getEmail());	
					adm.setUserName(admin.getUserName());
					adm.setPassword(admin.getPassword());
					
					session.beginTransaction();
					//save and update fetched data
					//pass contains the updated values
					session.saveOrUpdate(adm);
					//commit
					session.getTransaction().commit();
					
					return adm;// return passenger entity
					}
					catch (HibernateException e) {
						System.out.println("hibernate exception is: "+ e);
					}
						
					catch (Exception e) {
						System.out.println("exception is: "+ e);
					}
				return null;
			}


}
