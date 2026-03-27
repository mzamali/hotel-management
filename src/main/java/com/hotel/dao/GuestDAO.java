package com.hotel.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.List;
import com.hotel.util.HibernateUtil;
import com.hotel.model.Guest;

public class GuestDAO {
    public boolean addGuest(Guest guest) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(guest);
            transaction.commit();
            return true;

        }catch(Exception e) {
            if(transaction != null) {
                transaction.rollback();
                

            }
            e.printStackTrace();
           

        }
         return false;

    }
    public Guest getById (int id) {
       
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
           return session.get(Guest.class, id);

            
        } 
    }
    public List<Guest> getAllGuests() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Guest> query = session.createQuery("FROM Guest",Guest.class);
            return query.list();

        }
    }
    public boolean deleteGuest(int  id) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction =session.beginTransaction();
            Guest guest = session.get(Guest.class, id);
            if(guest !=null) {
                session.delete(guest);
                transaction.commit();
                return true;
            }
            
        } catch (Exception e) {
            if(transaction !=null) {
                transaction.rollback();
            }
            e.printStackTrace();
          
        }
          return false;
    }
    public boolean updateGuest(Guest guest) {
        Transaction transaction = null;
        try (Session session =HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
           session.update(guest);
           transaction.commit();
           return true;
            
        } catch (Exception e) {
            if(transaction !=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
    
}
