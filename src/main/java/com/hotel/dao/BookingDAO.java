package com.hotel.dao;

import com.hotel.model.Booking;
import com.hotel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class BookingDAO {
    
        
    public boolean saveBooking(Booking booking) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(booking);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
   
    public Booking getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Booking.class, id);
        }
    }
    
   
    public List<Booking> getAllBookings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Booking> query = session.createQuery("FROM booking", Booking.class);
            return query.list();
        }
    }
   public List<Booking> getBookingsByGuestId(int guestId) {
   
    try(Session session =HibernateUtil.getSessionFactory().openSession()) {
        Query<Booking> query = session.createQuery("FROM Booking WHERE guest.id  = :guestId",Booking.class);
        query.setParameter("guestId", guestId);
        return query.list();

        
    } 
    
}
    public List<Booking> getBookingsByDateRange(String startDate, String endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Booking> query = session.createQuery(
                "FROM Booking WHERE checkIn >= :start AND  checkOut <= :end ", 
                Booking.class);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        }
    }
        
    public boolean updateBooking(Booking booking) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(booking);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
        
    public boolean deleteBooking(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Booking booking = session.get(Booking.class, id);
            
            if (booking != null) {
                session.delete(booking);
                transaction.commit();
                return true;
            }
            return false;
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}